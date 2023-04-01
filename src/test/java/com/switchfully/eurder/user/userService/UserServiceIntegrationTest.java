package com.switchfully.eurder.user.userService;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import com.switchfully.eurder.user.service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceIntegrationTest {
    private static final CustomerDTO customerDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final User customer = new Customer(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"), "123");

    UserRepository userRepository = new UserRepository();
    UserMapper userMapper = new UserMapper();
    UserService userService = new UserService(userRepository, userMapper);

    @DisplayName("Register a Customer")
    @Test
    void givenACustomer_SaveItInTheRepository(){
        userService.registerACustomer(customerDTO);

        assertThat(customer).isEqualTo(userRepository.getAUserByContactInformation(customerDTO.getContactInformation()));
    }
}
