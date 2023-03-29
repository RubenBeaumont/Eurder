package com.switchfully.eurder.admin;

import com.switchfully.eurder.user.api.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
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
                    "0619102224"));
    private static final User joachim = new Customer(
            new Name("Joachim", "Hermann"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "jojo11@gmail.be",
                    "0478995566"));
    @Autowired
    UserRepository userRepository = new UserRepository();
    UserMapper userMapper = new UserMapper();

    @Value("8080")
    private int port;


    @Test
    void addItem_givenAnItem_thenNewlyAddedItemIsSavedInStockAndReturned(){
        PostItemDTO postItemDTO = new PostItemDTO(
                "Television", "60 inches flat screen", 1199.99, 5);

        ItemDTO itemDTO =
                RestAssured
                        .given()
                        .body(postItemDTO)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/admin/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(ItemDTO.class);

        assertThat(itemDTO.getName()).isEqualTo(postItemDTO.getName());
        assertThat(itemDTO.getDescription()).isEqualTo(postItemDTO.getDescription());
        assertThat(itemDTO.getPrice()).isEqualTo(postItemDTO.getPrice());

    }

    @Test
    void getAllCustomers_ReturnsAllCustomers_andOnlyCustomers(){
        userRepository.addUser(leonor);
        userRepository.addUser(joachim);

        List<UserDTO> listOfCustomers =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/admin/customers")
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

    @Test
    void getACustomer_givenAnID_thenReturnsACustomer(){
        userRepository.addUser(leonor);
        userRepository.addUser(joachim);

        UserDTO userDTO =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/admin/customers/" + leonor.getUserID())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(UserDTO.class);

        assertThat(userDTO).isEqualTo(userMapper.toDTO(leonor));
    }
}
