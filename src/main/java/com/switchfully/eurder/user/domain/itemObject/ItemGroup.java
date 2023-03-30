package com.switchfully.eurder.user.domain.itemObject;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;

import java.time.LocalDate;

public class ItemGroup {
    private final ItemDTO itemDTO;
    private LocalDate shippingDate;

    public ItemGroup(ItemDTO itemDTO) {
        this.shippingDate = LocalDate.now().plusDays(1);
        this.itemDTO = itemDTO;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }
    public LocalDate getShippingDate() {
        return shippingDate;
    }
    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }
}
