package com.switchfully.eurder.user.api.controller;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderReviewDTO;
import com.switchfully.eurder.user.service.security.Feature;
import com.switchfully.eurder.user.service.security.SecurityService;
import com.switchfully.eurder.user.service.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final SecurityService securityService;

    public CustomerController(CustomerService customerService, SecurityService securityService) {
        this.customerService = customerService;
        this.securityService = securityService;
    }

    @PostMapping(path = "orders", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderAnItem(@RequestBody List<ItemGroupDTO> itemGroupDTOList, @RequestHeader String authorization){
        int customerID = securityService.validateAuthorization(authorization, Feature.ORDER_ITEM);
        return customerService.orderAnItem(customerID, itemGroupDTOList);
    }

    @GetMapping(path = "orders", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderReviewDTO getAllMyOrders(@RequestHeader String authorization){
        int customerID = securityService.validateAuthorization(authorization, Feature.GET_ALL_MY_ORDERS);
        return customerService.getAllMyOrders(customerID);
    }

    @GetMapping(path = "items", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getAllItems(@RequestHeader String authorization){
        securityService.validateAuthorization(authorization, Feature.GET_ALL_ITEMS);
        return customerService.getAllItems();
    }
}
