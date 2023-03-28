package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.api.dto.UserDTO;
import com.switchfully.eurder.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final UserService userService;
    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerACustomer(@RequestBody CustomerDTO customerDTO){
        return userService.registerACustomer(customerDTO);
    }
}
