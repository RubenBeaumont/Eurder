package com.switchfully.eurder.user.domain.itemObject;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;

import java.util.Objects;

public class Item {
    private final int itemID;
    private static int counter;
    private String name;
    private String description;
    private double price;
    private int amount;


    public Item(String name, String description, double price, int amount) {
        this.itemID = ++counter;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public Item(PostItemDTO postItemDTO){
        this.itemID = ++counter;
        this.name = postItemDTO.getName();
        this.description = postItemDTO.getDescription();
        this.price = postItemDTO.getPrice();
        this.amount = postItemDTO.getAmount();
    }

    public ItemDTO toDTO(){
        return new ItemDTO(this);
    }

    public int getItemID() {
        return itemID;
    }
    public static int getCounter() {
        return counter;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemID == item.itemID && Double.compare(item.price, price) == 0 && amount == item.amount && Objects.equals(name, item.name) && Objects.equals(description, item.description);
    }
    @Override
    public int hashCode() {
        return Objects.hash(itemID, name, description, price, amount);
    }
}
