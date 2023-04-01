package com.switchfully.eurder.customer.orderRepository;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;
import com.switchfully.eurder.user.domain.itemObject.ItemGroup;
import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.repository.OrderRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderRepositoryTest {
    OrderRepository orderRepository = new OrderRepository();

    private static final User customer = new Customer(
            new Name("Jeff", "Jeffson"),
            new ContactInformation(
                    new Address("BerkendaelStraat", 26, "1190", "Forest"),
                    "jeff@hotmail.be",
                    "0478280818"), "123");
    private static final ItemGroupDTO itemGroupDTO = new ItemGroupDTO(1, 1);
    private static final ItemGroup itemGroup = new ItemGroup(itemGroupDTO);
    private static final List<ItemGroup> itemGroupList = new ArrayList<>();
    private static final Order order = new Order(itemGroupList);

    @Nested
    @DisplayName("Create an Order")
    class createAnOrder{
        @Test
        void givenACustomerAndAnOrder_createAnOrderAndSaveIt(){
            itemGroupList.add(itemGroup);
            assertThat(orderRepository.createAnOrder(customer, order)).isEqualTo(order);
            assertThat(orderRepository.getOrderDataBase()).isNotEmpty().isNotNull();
        }
    }

    @Nested
    @DisplayName("Get Orders by customer")
    class getOrdersByKey{
        @Test
        void givenACustomer_ifCustomerHasAlreadyOrderedBefore_thenReturnsAListOfAllOrdersMade(){
            orderRepository.createAnOrder(customer, order);
            assertThat(orderRepository.getOrdersByKey(customer)).containsExactly(order);
        }

        @Test
        void givenACustomer_ifCustomerHasNeverOrderedBefore_thenThrowAnException(){
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> orderRepository.getOrdersByKey(customer));

            assertEquals("No orders have been made by this customer.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Has Customer already ordered")
    class hasCustomerAlreadyOrdered{
        @Test
        void givenACustomer_ifCustomerHasAlreadyOrderedBefore_thenReturnsTrue(){
            orderRepository.createAnOrder(customer, order);

            assertThat(orderRepository.hasCustomerAlreadyOrdered(customer)).isTrue();
        }

        @Test
        void givenACustomer_ifCustomerHasNeverOrderedBefore_thenReturnsFalse(){
            assertThat(orderRepository.hasCustomerAlreadyOrdered(customer)).isFalse();
        }
    }
}
