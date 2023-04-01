package com.switchfully.eurder.user.userRepository;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    @DisplayName("Add a User")
    class addUser {
        @Test
        void givenAUser_thenSaveItInTheRepository() {
            userRepository.addUser(customer);

            assertThat(customer).isEqualTo(userRepository.getAUserByID(customer.getUserID()));
        }
    }

    @Nested
    @DisplayName("Does this User exist")
    class doesUserExist {
        @Test
        void givenAUser_thenCheckTheRepository_ifPresentReturnsTrue() {
            userRepository.addUser(customer);

            assertThat(userRepository.doesUserExist(customerDTO)).isTrue();
        }

        @Test
        void givenAUser_thenCheckTheRepository_IfNotPresentReturnsFalse() {
            assertThat(userRepository.doesUserExist(customerDTO)).isFalse();
        }
    }

    @Nested
    @DisplayName("Get all Customers")
    class getAllCustomers{
        @Test
        void whenThereAreCustomersRegistered_returnsAListOfAllCustomers(){
            userRepository.addUser(customer);
            assertThat(userRepository.getAllCustomers()).isNotEmpty().isNotNull();
            assertThat(userRepository.getAllCustomers()).containsExactly(customer);
        }
        @Test
        void doesNotReturnAdmins_ifNoCustomersArePresent_returnsAnEmptyList(){
            assertThat(userRepository.getAllCustomers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Get a User by ID")
    class getAUserByID {
        @Test
        void givenAnID_ifAnIDMatch_thenReturnsTheCorrespondingCustomer(){
            userRepository.addUser(customer);
            assertThat(userRepository.getAUserByID(customer.getUserID())).isEqualTo(customer);
        }
        @Test
        void givenAnID_ifNoIDMatch_thenThrowAnException(){
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userRepository.getAUserByID(10));

            assertEquals("No customer was found for id " + 10 + ".", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Get a User by e-mail")
    class getAUserByEmail{
        @Test
        void givenAnEmail_ifAnEmailMatch_thenReturnsTheCorrespondingCustomer(){
            userRepository.addUser(customer);
            assertThat(userRepository.getAUserByEmail(customer.getContactInformation().emailAddress())).isEqualTo(customer);
        }

        @Test
        void givenAnEmail_ifNoEmailMatch_thenThrowAnException(){
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userRepository.getAUserByEmail(customer.getContactInformation().emailAddress()));

            assertEquals("No customer found for that e-mail address.", exception.getMessage());
        }
    }
}
