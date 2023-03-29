package com.switchfully.eurder.user.api.controller;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.service.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping(path = "items", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addItem(@RequestBody PostItemDTO postItemDTO){
        return adminService.addItem(postItemDTO);
    }

    @GetMapping(path = "customers", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllCustomers(){
       return adminService.getAllCustomers();
    }

    @GetMapping(path = "customers/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getACustomer(@PathVariable int id){
        return adminService.getACustomer(id);
    }
}
