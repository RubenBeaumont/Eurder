package com.switchfully.eurder.user.domain.itemObject;

import java.util.List;

public class Order {
    private final List<ItemGroup> itemGroupList;

    public Order(List<ItemGroup> itemGroupList) {
        this.itemGroupList = itemGroupList;
    }
}
