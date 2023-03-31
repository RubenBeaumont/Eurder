package com.switchfully.eurder.user.api.dto.itemDTO;

import java.time.LocalDate;

public class ItemGroupDTO {
    private final int itemID;
    private final int amount;
//    private LocalDate shippingDate = LocalDate.now().plusDays(1);
//    private double price = 0;

    public ItemGroupDTO(int itemID, int amount) {
        this.itemID = itemID;
        this.amount = amount;
    }

    public int getItemID() {
        return itemID;
    }
    public int getAmount() {
        return amount;
    }
//    public LocalDate getShippingDate() {
//        return shippingDate;
//    }
//    public double getPrice() {
//        return price;
//    }
//
//    public void setShippingDate(LocalDate shippingDate) {
//        this.shippingDate = shippingDate;
//    }
//    public void setPrice(double price) {
//        this.price = price;
//    }
}
