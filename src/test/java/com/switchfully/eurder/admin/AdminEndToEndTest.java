package com.switchfully.eurder.admin;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Admin;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AdminController E2E Testing")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminEndToEndTest {
    private static final User leonor = new Customer(
            new Name("Léonor", "Bauguen"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "l.bauguen56@gmail.fr",
                    "0619102224"), "123");
    private static final User joachim = new Customer(
            new Name("Joachim", "Hermann"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "jojo11@gmail.be",
                    "0478995566"), "123");
    private static final User ruben = new Admin(
            new Name("Ruben", "Beaumont"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "admin@gmail.be",
                    "0471260818"), "123");
    private static final Item originalItem = new Item("TV", "a television", 995.95, 10);
    private static final ItemDTO updatedItem = new ItemDTO(1, "Television", "60 inches flat screen", 995.95, 12);
    @Autowired
    UserRepository userRepository = new UserRepository();
    ItemRepository itemRepository = new ItemRepository();
    UserMapper userMapper = new UserMapper();

    @Value("8080")
    private int port;

    @Nested
    @DisplayName("Add an Item")
    class addItem {
        @Test
        void givenAnItem_thenItemIsSavedInStockAndReturned() {
            PostItemDTO postItemDTO = new PostItemDTO(
                    "Television", "60 inches flat screen", 1199.99, 5);

            ItemDTO itemDTO =
                    RestAssured
                            .given()
                            .auth()
                            .preemptive()
                            .basic("admin@gmail.be", "123")
                            .body(postItemDTO)
                            .accept(JSON)
                            .contentType(JSON)
                            .when()
                            .port(port)
                            .post("/admins/items")
                            .then()
                            .assertThat()
                            .statusCode(HttpStatus.CREATED.value())
                            .extract()
                            .as(ItemDTO.class);

            assertThat(itemDTO.getName()).isEqualTo(postItemDTO.getName());
            assertThat(itemDTO.getDescription()).isEqualTo(postItemDTO.getDescription());
            assertThat(itemDTO.getPrice()).isEqualTo(postItemDTO.getPrice());
            assertThat(itemDTO.getAmount()).isEqualTo(postItemDTO.getAmount());

        }
    }

    @Nested
    @DisplayName("Get all Customers")
    class getAllCustomers {
        @Test
        void returnsAllCustomers_andOnlyCustomers() {
            userRepository.addUser(leonor);
            userRepository.addUser(joachim);

            List<UserDTO> listOfCustomers =
                    RestAssured
                            .given()
                            .auth()
                            .preemptive()
                            .basic("admin@gmail.be", "123")
                            .contentType(JSON)
                            .when()
                            .port(port)
                            .get("/admins/customers")
                            .then()
                            .assertThat()
                            .statusCode(HttpStatus.OK.value())
                            .extract()
                            .body()
                            .jsonPath()
                            .getList(".", UserDTO.class);

            assertThat(listOfCustomers).isNotEmpty().isNotNull();
            assertThat(listOfCustomers).containsExactly(userMapper.toDTO(leonor), userMapper.toDTO(joachim));
        }
    }

    @Nested
    @DisplayName("Get a Customer")
    class getACustomer {
        @Test
        void givenAnID_ifThereIsAMatchForThisID_thenReturnsThisCustomer() {
            userRepository.addUser(leonor);
            userRepository.addUser(joachim);

            UserDTO userDTO =
                    RestAssured
                            .given()
                            .auth()
                            .preemptive()
                            .basic("admin@gmail.be", "123")
                            .contentType(JSON)
                            .when()
                            .port(port)
                            .get("/admins/customers/" + leonor.getUserID())
                            .then()
                            .assertThat()
                            .statusCode(HttpStatus.OK.value())
                            .extract()
                            .as(UserDTO.class);

            assertThat(userDTO).isEqualTo(userMapper.toDTO(leonor));
        }

        @Test
        void givenAnID_ifCustomerIsNotPresent_thenThrowsA404NotFound() {
            RestAssured
                    .given()
                    .auth()
                    .preemptive()
                    .basic("admin@gmail.be", "123")
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .get("/admins/customers/2")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("Update an Item")
    class updateItem{
//        @Test
//        void givenItemUpdates_ifAMatchIsFound_thenUpdateTheCorrespondingItem(){
//            itemRepository.addItem(originalItem);
//
//            ItemDTO itemDTO =
//                    RestAssured
//                            .given()
//                            .auth()
//                            .preemptive()
//                            .basic("admin@gmail.be", "123")
//                            .body(updatedItem)
//                            .accept(JSON)
//                            .contentType(JSON)
//                            .when()
//                            .port(port)
//                            .put("/admins/items")
//                            .then()
//                            .assertThat()
//                            .statusCode(HttpStatus.OK.value())
//                            .extract()
//                            .as(ItemDTO.class);
//
//            assertThat(itemDTO).isEqualTo(updatedItem);
//        }
        @Test
        void givenItemUpdates_ifNoMatchIsFound_thenThrowAnException(){
            RestAssured
                    .given()
                    .auth()
                    .preemptive()
                    .basic("admin@gmail.be", "123")
                    .body(updatedItem)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .put("/admins/items")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }
}
