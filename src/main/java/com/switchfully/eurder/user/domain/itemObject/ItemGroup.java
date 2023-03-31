package com.switchfully.eurder.user.domain.itemObject;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemGroup {
    private final int itemID;
    private final int amount;
    private LocalDate shippingDate;
    private double price;

    public ItemGroup(int itemID, int amount, LocalDate shippingDate, double price) {
        this.itemID = itemID;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.price = price;
    }

    public ItemGroup(ItemGroupDTO itemGroupDTO){
        this.itemID = itemGroupDTO.getItemID();
        this.amount = itemGroupDTO.getAmount();
        this.shippingDate = LocalDate.now().plusDays(1);
        this.price = 0;
    }

    public int getItemID() {
        return itemID;
    }
    public int getAmount() {
        return amount;
    }
    public LocalDate getShippingDate() {
        return shippingDate;
    }
    public double getPrice() {
        return price;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
