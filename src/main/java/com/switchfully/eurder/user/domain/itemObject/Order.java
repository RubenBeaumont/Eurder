package com.switchfully.eurder.user.domain.itemObject;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<ItemGroup> itemGroupList = new ArrayList<>();
    private final double price;

    public Order(ItemGroup itemGroup) {
        this.itemGroupList.add(itemGroup);
        this.price = calculateTotalPrice();
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public double calculateTotalPrice(){
        return itemGroupList.stream()
                .mapToDouble(item -> item.getItemDTO().getAmount()*item.getItemDTO().getPrice()).sum();
    }

    public double getPrice() {
        return price;
    }
}
