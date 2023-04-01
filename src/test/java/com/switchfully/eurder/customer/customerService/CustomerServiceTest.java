package com.switchfully.eurder.customer.customerService;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;
import com.switchfully.eurder.user.domain.itemObject.ItemGroup;
import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.OrderRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.OrderMapper;
import com.switchfully.eurder.user.service.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.inOrder;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceTest {
    private final OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final ItemRepository itemRepositoryMock = Mockito.mock(ItemRepository.class);
    private final ItemMapper itemMapperMock = Mockito.mock(ItemMapper.class);
    private final OrderMapper orderMapperMock = Mockito.mock(OrderMapper.class);
    private final CustomerService customerService = new CustomerService(orderRepositoryMock, userRepositoryMock, itemRepositoryMock, itemMapperMock, orderMapperMock);
    private static final ItemGroupDTO itemGroupDTO = new ItemGroupDTO(1, 1);
    private static final List<ItemGroupDTO> itemGroupListDTO = new ArrayList<>();
    private static final ItemGroup itemGroup = new ItemGroup(itemGroupDTO);
    private static final List<ItemGroup> itemGroupList = new ArrayList<>();
    private static final Order order = new Order(itemGroupList);

    private static final User customer = new Customer(new Name("Jeff", "Jeffson"),
            new ContactInformation(new Address("BerkendaelStraat", 26, "1190", "Forest"), "jeff@hotmail.be", "0478280818"), "123");


//    @Nested
//    @DisplayName("Order an Item")
//    class orderAnItem{
//        @Test
//        void givenACustomerIDAndAListOfItemGroup_firstlyModifyTheStockInItemRepository_Secondly(){
//            itemGroupList.add(itemGroup);
//            customerService.orderAnItem(1, itemGroupListDTO);
//
//            Mockito.verify(orderRepositoryMock).createAnOrder(customer, order);
//        }
//    }

    @Nested
    @DisplayName("Get all Items")
    class getAllItems{
        @Test
        void callsGetAllItemsInItemRepository(){
            customerService.getAllItems();

            Mockito.verify(itemRepositoryMock).getAllItems();
        }
    }

    @Nested
    @DisplayName("Get all my Orders")
    class getAllMyOrders{
        @Test
        void firstlyCallsGetAUserByIdInUserRepository_secondlyCallsGetOrdersByKeyInOrderRepository(){
            customerService.getAllMyOrders(1);
            InOrder expectedExecutionFlow = inOrder(userRepositoryMock, orderRepositoryMock);

            expectedExecutionFlow.verify(userRepositoryMock).getAUserByID(1);
            expectedExecutionFlow.verify(orderRepositoryMock).getOrdersByKey(Mockito.any());
        }
    }
}
