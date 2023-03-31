package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.userObject.User;
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
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderDataBase.put(customer, orderList);
        return order;
    }

    public List<Order> getOrdersByKey(User customer){
        return orderDataBase.get(customer);
    }

    public boolean hasCustomerAlreadyOrdered(User customer){
        return orderDataBase.keySet().stream()
                .anyMatch(key -> key.equals(customer));
    }
}
