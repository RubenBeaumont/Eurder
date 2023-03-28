package com.switchfully.eurder.user.api.controller;

import com.switchfully.eurder.user.api.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerACustomer(@RequestBody CustomerDTO customerDTO){
        return userService.registerACustomer(customerDTO);
    }
}
