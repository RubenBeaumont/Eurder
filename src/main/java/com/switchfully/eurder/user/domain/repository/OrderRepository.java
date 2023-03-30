package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
    private final UserRepository userRepository;
    private Map<User, Order> orderDataBase;

    public OrderRepository(UserRepository userRepository, Map<Customer, Order> orderDataBase) {
        this.userRepository = userRepository;
        this.orderDataBase = new HashMap<>();
    }

    public Order addToOrder(int id, Order order){
        orderDataBase.put(userRepository.getAUserByID(id), order);
        return order;
    }
}
