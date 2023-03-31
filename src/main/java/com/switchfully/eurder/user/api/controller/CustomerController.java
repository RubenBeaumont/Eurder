package com.switchfully.eurder.user.api.controller;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.service.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "{customerID}/items", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderAnItem(@PathVariable int customerID, @RequestBody List<ItemGroupDTO> itemGroupDTOList){
        return customerService.orderAnItem(customerID, itemGroupDTOList);
    }

    @GetMapping(path = "items", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getAllItems(){
        return customerService.getAllItems();
    }
}
