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
                    "0478280818"));
    private static final CustomerDTO wrongEmailDTO = new CustomerDTO(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeffhotmail.be",
                    "0478280818"));


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
    @DisplayName("Is the user profile valid")
    class isUserProfileValid {
        @Test
        void givenACustomer_ifAllFieldsAreValid_thenReturnsTrue() {
            assertThat(userServiceMock.isUserProfileValid(customerDTO)).isTrue();
        }

        @Test
        void givenACustomer_ifEmailIsNotValid_thenReturnsFalse() {
            assertThat(userServiceMock.isUserProfileValid(wrongEmailDTO)).isFalse();
        }
    }
}
