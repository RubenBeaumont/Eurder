package com.switchfully.eurder.admin.adminService;

import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import com.switchfully.eurder.user.service.service.AdminService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminServiceIntegrationTest {
    private static final PostItemDTO televisionDTO =
            new PostItemDTO("Television", "60 inches flat screen", 1199.99, 5);
    private static final User leonor = new Customer(
            new Name("Léonor", "Bauguen"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "l.bauguen56@gmail.fr",
                    "0619102224"));
    private static final User joachim = new Customer(
            new Name("Joachim", "Hermann"),
            new ContactInformation(
                    new Address("Rue Berkendael", 26, "1190", "Forest"),
                    "jojo11@gmail.be",
                    "0478995566"));
    private final UserRepository userRepository = new UserRepository();
    private final ItemRepository itemRepository = new ItemRepository();
    private final UserMapper userMapper = new UserMapper();
    private final ItemMapper itemMapper = new ItemMapper();
    private final AdminService adminService = new AdminService(userRepository, itemRepository, userMapper, itemMapper);

    @Nested
    class addItem {
        @Test
        void whenIAddAnItem_thenTheRepositoryContainsThisItem() {
            adminService.addItem(televisionDTO);

            assertThat(itemRepository.isItemInStock(televisionDTO)).isTrue();
        }

        @Test
        void ifItIsAlreadyInStock_thenAddAmountToExistingItem() {
            adminService.addItem(televisionDTO);
            adminService.addItem(televisionDTO);
            assertThat(itemRepository.getAnItemByProperties(televisionDTO).getAmount()).isEqualTo(10);
        }
    }

    @Nested
    class getAllCustomers {
        @Test
        void ReturnsAllCustomers() {
            userRepository.addUser(leonor);
            userRepository.addUser(joachim);

            assertThat(adminService.getAllCustomers()).isNotEmpty().isNotNull();
            assertThat(adminService.getAllCustomers()).containsExactly(userMapper.toDTO(leonor), userMapper.toDTO(joachim));
        }
    }

    @Nested
    class getACustomer {
        @Test
        void whenThereIsOneCustomerInTheRepository_thenICanRetrieveItByID() {
            userRepository.addUser(leonor);

            assertThat(adminService.getACustomer(leonor.getUserID())).isEqualTo(userMapper.toDTO(leonor));
        }

        @Test
        void whenTheRepositoryIsEmpty_thenIReceiveA404WhenRequestingACustomerByID() {
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> adminService.getACustomer(leonor.getUserID()));

            assertEquals("No customer was found for id " + leonor.getUserID() + ".", exception.getMessage());
        }
    }
}
