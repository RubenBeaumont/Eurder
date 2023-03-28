package com.switchfully.eurder.user.userRepository;

import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.roles.Customer;
import com.switchfully.eurder.user.domain.userDetails.Address;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
    private static final User customer = new Customer(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address(
                            "BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478/280818"));

    @Test
    void addUser_givenAUser_thenSavedItInTheRepository(){
        userRepository.addUser(customer);

        assertThat(customer).isEqualTo(userRepository.getAUserByContactInformation(customer.getContactInformation()));
    }
}
