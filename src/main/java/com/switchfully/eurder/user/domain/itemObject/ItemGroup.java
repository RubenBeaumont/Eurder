package com.switchfully.eurder.user.domain.itemObject;

import com.switchfully.eurder.user.domain.repository.ItemRepository;

import java.time.LocalDate;

public class ItemGroup {
    private final Item item;
    private final LocalDate shippingDate;

    public ItemGroup(Item item) {
        this.shippingDate = LocalDate.now().plusDays(1);
        this.item = item;
    }
}
