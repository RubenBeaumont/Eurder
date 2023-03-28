package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.api.dto.UserDTO;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.roles.Customer;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO registerACustomer(CustomerDTO customerDTO){
        User newCustomer = new Customer(customerDTO);
        userRepository.addUser(newCustomer);
        return userMapper.toDTO(newCustomer);
    }
}
