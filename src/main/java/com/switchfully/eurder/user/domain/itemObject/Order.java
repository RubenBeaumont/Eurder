package com.switchfully.eurder.user.domain.itemObject;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;

import java.util.List;

public class Order {
    private final int orderID;
    private int counter;
    private final List<ItemGroup> itemGroupList;
    private final double price;

    public Order(List<ItemGroup> itemGroupList) {
        this.orderID = ++counter;
        this.itemGroupList = itemGroupList;
        this.price = calculateTotalPrice();
    }


    public double calculateTotalPrice(){
        return itemGroupList.stream()
                .mapToDouble(ItemGroup::getPrice).sum();
    }

    public int getOrderID() {
        return orderID;
    }
    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }
    public double getPrice() {
        return price;
    }
}
