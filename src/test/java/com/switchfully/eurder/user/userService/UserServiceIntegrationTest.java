package com.switchfully.eurder.user.userService;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.roles.Customer;
import com.switchfully.eurder.user.domain.userDetails.Address;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;
import com.switchfully.eurder.user.service.UserMapper;
import com.switchfully.eurder.user.service.UserService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceIntegrationTest {
    private static final CustomerDTO customerDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address(
                            "BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478/280818"));
    private static final User customer = new Customer(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address(
                            "BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478/280818"));

    UserRepository userRepository = new UserRepository();
    UserMapper userMapper = new UserMapper();
    UserService userService = new UserService(userRepository, userMapper);


    @Test
    void registerACustomer_givenACustomer_SaveItInTheRepository(){
        userService.registerACustomer(customerDTO);

        assertThat(customer).isEqualTo(userRepository.getAUserByContactInformation(customerDTO.getContactInformation()));
    }
}
