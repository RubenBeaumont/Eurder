package com.switchfully.eurder.user.userService;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.InvalidParametersException;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import com.switchfully.eurder.user.service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceTest {
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapperMock = Mockito.mock(UserMapper.class);
    private final UserMapper userMapper = new UserMapper();
    private final UserService userServiceMock = new UserService(userRepositoryMock, userMapperMock);
    private final UserService userService = new UserService(userRepository, userMapper);

    private static final CustomerDTO customerDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO firstNameIsNullDTO = new CustomerDTO(
            new Name(null, "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO lastNameIsNullDTO = new CustomerDTO(
            new Name("Jeff", null),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO streetNameIsNullDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address(null, 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO streetNumberIsNullDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 0, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO wrongPostCodelDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190a225", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO cityIsNullDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", null),
                    "jeff@hotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO wrongEmailDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeffhotmail.be",
                    "0478280818"),
            "123");
    private static final CustomerDTO wrongPhoneNumberDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "1478280818"),
            "123");
    private static final CustomerDTO passwordIsNullDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"),
            null);


    @Nested
    @DisplayName("Register a Customer")
    class registerACustomer {
        @Test
        void givenACustomer_thenFirstlyAddItToTheRepositoryAndSecondlyConvertItToADTO() {
            userServiceMock.registerACustomer(customerDTO);
            InOrder expectedExecutionFlow = inOrder(userRepositoryMock, userMapperMock);
            expectedExecutionFlow.verify(userRepositoryMock).addUser(Mockito.any(User.class));
            expectedExecutionFlow.verify(userMapperMock).toDTO(Mockito.any());
        }

        @Test
        void givenACustomer_ifUserProfileIsNotValid_thenThrowAnException(){
            InvalidParametersException exception = assertThrows(InvalidParametersException.class,
                    () -> userService.registerACustomer(wrongEmailDTO));

            assertEquals("Input is invalid, either a field is empty or incorrect.", exception.getMessage());
        }

        @Test
        void givenACustomer_ifUserAlreadyExist_thenThrowAnException(){
            InvalidParametersException exception = assertThrows(InvalidParametersException.class,
                    () -> {
                userService.registerACustomer(customerDTO);
                userService.registerACustomer(customerDTO);
            });

            assertEquals("Input is invalid, email or phone-number is already in use.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Is the User profile valid")
    class isUserProfileValid {
        @Test
        void givenACustomer_ifAllFieldsAreValid_thenReturnsTrue() {
            assertThat(userServiceMock.isUserProfileValid(customerDTO)).isTrue();
        }
        @Test
        void givenACustomer_ifFirstNameIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(firstNameIsNullDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifLastNameIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(lastNameIsNullDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifStreetNameIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(streetNameIsNullDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifPostCodeIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(wrongPostCodelDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifCityIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(cityIsNullDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifEmailIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(wrongEmailDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifPhoneNumberIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(wrongPhoneNumberDTO)).isFalse();
        }
        @Test
        void givenACustomer_ifPasswordIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(passwordIsNullDTO)).isFalse();
        }
    }
}
