package com.switchfully.eurder.user.api.controller;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.service.security.Feature;
import com.switchfully.eurder.user.service.security.SecurityService;
import com.switchfully.eurder.user.service.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admins")
public class AdminController {
    private final AdminService adminService;
    private final SecurityService securityService;

    public AdminController(AdminService adminService, SecurityService securityService) {
        this.adminService = adminService;
        this.securityService = securityService;
    }

    @PostMapping(path = "items", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addItem(@RequestBody PostItemDTO postItemDTO, @RequestHeader String authorization){
        securityService.validateAuthorization(authorization, Feature.ADD_ITEM);
        return adminService.addItem(postItemDTO);
    }

    @GetMapping(path = "customers", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllCustomers(@RequestHeader String authorization){
        securityService.validateAuthorization(authorization, Feature.GET_ALL_CUSTOMERS);
        return adminService.getAllCustomers();
    }

    @GetMapping(path = "customers/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getACustomer(@PathVariable int id, @RequestHeader String authorization){
        securityService.validateAuthorization(authorization, Feature.GET_A_CUSTOMER);
        return adminService.getACustomer(id);
    }

    @PutMapping(path = "items", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO updateItem(@RequestBody ItemDTO itemDTO, @RequestHeader String authorization){
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEM);
        return adminService.updateItem(itemDTO);
    }
}
