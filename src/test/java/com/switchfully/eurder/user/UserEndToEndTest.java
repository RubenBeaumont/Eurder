package com.switchfully.eurder.user;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserController E2E Testing")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserEndToEndTest {
    @Autowired
    UserRepository userRepository;
    @Value("8085")
    private int port;

    @DisplayName("Register a Customer")
    @Test
    void givenACustomerToRegister_thenNewlyRegisteredCustomerIsSavedAndReturned(){
        CustomerDTO customerDTO = new CustomerDTO(
                new Name("Léonor", "Bauguen"),
                new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "l.bauguen56@gmail.fr", "0619102224"), "123");

        UserDTO userDTO =
                RestAssured
                        .given()
                        .body(customerDTO)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/users/registration")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(UserDTO.class);

        assertThat(userDTO.getName().firstName())
                .isEqualTo(customerDTO.getName().firstName());
        assertThat(userDTO.getName().lastName())
                .isEqualTo(customerDTO.getName().lastName());
        assertThat(userDTO.getContactInformation().emailAddress())
                .isEqualTo(customerDTO.getContactInformation().emailAddress());
        assertThat(userDTO.getContactInformation().phoneNumber())
                .isEqualTo(customerDTO.getContactInformation().phoneNumber());
        assertThat(userDTO.getContactInformation().address().city())
                .isEqualTo(customerDTO.getContactInformation().address().city());
        assertThat(userDTO.getContactInformation().address().postalCode())
                .isEqualTo(customerDTO.getContactInformation().address().postalCode());
        assertThat(userDTO.getContactInformation().address().streetName())
                .isEqualTo(customerDTO.getContactInformation().address().streetName());
        assertThat(userDTO.getContactInformation().address().streetNumber())
                .isEqualTo(customerDTO.getContactInformation().address().streetNumber());
    }
}
