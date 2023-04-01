package com.switchfully.eurder.admin.adminService;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.InvalidParametersException;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import com.switchfully.eurder.user.service.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminServiceTest {
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final ItemRepository itemRepositoryMock = Mockito.mock(ItemRepository.class);
    private final UserMapper userMapperMock = Mockito.mock(UserMapper.class);
    private final ItemMapper itemMapperMock = Mockito.mock(ItemMapper.class);
    private final AdminService adminServiceMock = new AdminService(userRepositoryMock, itemRepositoryMock, userMapperMock, itemMapperMock);
    private static final PostItemDTO televisionDTO = new PostItemDTO("Television", "60 inches flat screen", 1199.99, 5);
    private static final ItemDTO updatedDTO = new ItemDTO(1, "Television", "60 inches flat screen", 1199.99, 5);

    private static final PostItemDTO defectTVDTO = new PostItemDTO("Television", null, 1199.99, 5);
    private static final User jeff = new Customer(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"), "123");
    @Nested
    @DisplayName("Add an Item")
    class addItem {
        @Test
        void givenAnItem_thenFirstlyAddItToTheRepositoryAndSecondlyConvertItToADTO() {
            adminServiceMock.addItem(televisionDTO);
            InOrder expectedExecutionFlow = inOrder(itemRepositoryMock, itemMapperMock);

            expectedExecutionFlow.verify(itemRepositoryMock).addItem(Mockito.any(Item.class));
            expectedExecutionFlow.verify(itemMapperMock).toDTO(Mockito.any());
        }

        @Test
        void givenAnItem_ifItemDetailsAreNotValid_thenThrowAnException(){
            InvalidParametersException exception = assertThrows(InvalidParametersException.class,
                    () -> adminServiceMock.addItem(defectTVDTO));

            assertEquals("Input is invalid, a field is empty.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Update an Item")
    class updateItem{
        @Test
        void givenUpdatedItem_thenUpdateMatchingIDItem(){
            adminServiceMock.addItem(televisionDTO);
            adminServiceMock.updateItem(updatedDTO);

            Mockito.verify(itemRepositoryMock).updateItem(updatedDTO);
        }
    }

    @Nested
    @DisplayName(("Is the item valid"))
    class isItemDetailsValid {
        @Test
        void givenAnItem_ifAllFieldsAreValid_thenReturnsTrue() {
            assertThat(adminServiceMock.isItemDetailsValid(televisionDTO)).isTrue();
        }

        @Test
        void givenAnItem_ifAFieldIsNull_thenReturnsFalse() {
            assertThat(adminServiceMock.isItemDetailsValid(defectTVDTO)).isFalse();
        }
    }

    @Nested
    @DisplayName("Get all Customers")
    class getAllCustomers {
        @Test
        void returnsAllCustomers() {
            adminServiceMock.getAllCustomers();

            Mockito.verify(userRepositoryMock).getAllCustomers();
        }

        @Test
        void ifNoCustomersAreRegistered_thenReturnsAnEmptyList(){
            assertThat(adminServiceMock.getAllCustomers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Get a Customer")
    class getACustomer {
        @Test
        void givenAnID_ifACustomerIDMatch_thenGetACustomerByIDInTheRepository() {
            userRepositoryMock.addUser(jeff);
            adminServiceMock.getACustomer(jeff.getUserID());

            Mockito.verify(userRepositoryMock).getAUserByID(jeff.getUserID());
        }
    }
}
