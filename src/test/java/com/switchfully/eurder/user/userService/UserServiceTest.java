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
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.inOrder;

@SpringBootTest
public class UserServiceTest {
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final UserMapper userMapperMock = Mockito.mock(UserMapper.class);
    private final UserService userService = new UserService(userRepositoryMock, userMapperMock);
    private static final CustomerDTO customerDTO = new CustomerDTO(
            new Name("Ruben", "Beaumont"),
            new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "beaumont-rubben@hotmail.fr", "0471/260818"));
    private static final User customer = new Customer(
            new Name("Ruben", "Beaumont"),
            new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "beaumont-rubben@hotmail.fr", "0471/260818"));

    @Test
    void registerACustomer_givenACustomer_thenFirstlyAddItToTheRepositoryAndSecondlyConvertItToADTO(){
        userService.registerACustomer(customerDTO);
        InOrder expectedExecutionFlow = inOrder(userRepositoryMock, userMapperMock);
        expectedExecutionFlow.verify(userRepositoryMock).addUser(Mockito.any(User.class));
        expectedExecutionFlow.verify(userMapperMock).toDTO(Mockito.any());
    }
}
