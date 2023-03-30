package com.switchfully.eurder.user.api.dto.itemDTO;

import com.switchfully.eurder.user.domain.itemObject.Item;

public class ItemDTO {
    private final int itemID;
    private final String name;
    private final String description;
    private final double price;
    private int amount;

    public ItemDTO(int itemID, String name, String description, double price, int amount) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public ItemDTO(Item toCopy){
        this.itemID = toCopy.getItemID();
        this.name = toCopy.getName();
        this.description = toCopy.getDescription();
        this.amount = toCopy.getAmount();
        this.price = toCopy.getPrice();
    }

    public int getItemID() {
        return itemID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
