package com.switchfully.eurder.user;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.api.dto.UserDTO;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.userDetails.Address;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;
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
    @Value("8080")
    private int port;

    @Test
    void registerACustomer_givenACustomerToRegister_thenNewlyRegisteredCustomerIsSavedAndReturned(){
        CustomerDTO customerDTO = new CustomerDTO(
                new Name("Ruben", "Beaumont"),
                new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "beaumont-rubben@hotmail.fr", "0471/260818"));

        UserDTO userDTO =
                RestAssured
                        .given()
                        .body(customerDTO)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/customer/register")
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
