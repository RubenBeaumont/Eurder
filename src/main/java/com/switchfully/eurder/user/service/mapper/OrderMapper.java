package com.switchfully.eurder.user.service.mapper;

import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.domain.itemObject.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderDTO toDTO(Order order){
        return new OrderDTO(order);
    }

    public List<OrderDTO> toDTOs(List<Order> orderList){
        return orderList.stream()
                .map(this::toDTO)
                .toList();
    }
}
