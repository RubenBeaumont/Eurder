package com.switchfully.eurder.user.userRepository;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
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

    @Test
    void addUser_givenAUser_thenSaveItInTheRepository(){
        userRepository.addUser(customer);

        assertThat(customer).isEqualTo(userRepository.getAUserByContactInformation(customer.getContactInformation()));
    }

    @Test
    void doesAUserExist_givenAUser_thenCheckTheRepository_ifPresentReturnsTrue(){
        userRepository.addUser(customer);

        assertThat(userRepository.doesUserExist(customerDTO)).isTrue();
    }

    @Test
    void doesAUserExist_givenAUser_thenCheckTheRepository_IfNotPresentReturnsFalse(){
        assertThat(userRepository.doesUserExist(customerDTO)).isFalse();
    }
}
