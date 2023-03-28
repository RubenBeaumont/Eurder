package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.service.mapper.UserMapper;
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
        return userMapper.toDTO(userRepository.addUser(newCustomer));
    }
}
