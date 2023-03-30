package com.switchfully.eurder.user.service.mapper;

import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.domain.itemObject.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDTO toDTO(Order order){
        return new OrderDTO(order);
    }
}
