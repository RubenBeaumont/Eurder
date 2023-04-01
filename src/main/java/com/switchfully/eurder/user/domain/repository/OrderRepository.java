package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<User, List<Order>> orderDataBase = new HashMap<>();

    public Order createAnOrder(User customer, Order order){
        if(hasCustomerAlreadyOrdered(customer)){
            getOrdersByKey(customer).add(order);
        }
        else {
            List<Order> orderList = new ArrayList<>();
            orderList.add(order);
            orderDataBase.put(customer, orderList);
        }
        return order;
    }

    public List<Order> getOrdersByKey(User customer){
        if(!hasCustomerAlreadyOrdered(customer)){
            throw new NotFoundException("No orders have been made by this customer.");
        }
        return orderDataBase.get(customer);
    }

    public boolean hasCustomerAlreadyOrdered(User customer){
        return orderDataBase.keySet().stream()
                .anyMatch(key -> key.equals(customer));
    }

    public Map<User, List<Order>> getOrderDataBase() {
        return orderDataBase;
    }
}
