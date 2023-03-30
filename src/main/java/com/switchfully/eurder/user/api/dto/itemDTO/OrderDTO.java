package com.switchfully.eurder.user.api.dto.itemDTO;

import com.switchfully.eurder.user.domain.itemObject.ItemGroup;
import com.switchfully.eurder.user.domain.itemObject.Order;

import java.util.List;

public class OrderDTO {
    private final List<ItemGroup> itemGroupList;
    private final double price;

    public OrderDTO(List<ItemGroup> itemGroupList, double price) {
        this.itemGroupList = itemGroupList;
        this.price = price;
    }

    public OrderDTO(Order toCopy) {
        this.itemGroupList = toCopy.getItemGroupList();
        this.price = toCopy.getPrice();
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }
    public double getPrice() {
        return price;
    }
}
