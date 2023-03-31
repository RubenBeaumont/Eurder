package com.switchfully.eurder.user.api.dto.itemDTO;

import java.util.List;

public class OrderReviewDTO {
    private final List<OrderDTO> orderDTOList;
    private double totalPrice;

    public OrderReviewDTO(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
        this.totalPrice = 0;
    }

    public List<OrderDTO> getOrderDTOList() {
        return orderDTOList;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
