package com.switchfully.eurder.user.api.dto.itemDTO;

public class ItemGroupDTO {
    private final int itemID;
    private final int amount;

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
}
