package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.api.dto.UserDTO;
import com.switchfully.eurder.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping(path = "users", produces = "application/json")
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserDTO> getAllUsers(){
//       return userService.getAllUsers();
//    }
}
