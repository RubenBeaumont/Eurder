package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.api.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {
    private final List<Item> stock = new ArrayList<>();

    public Item addItem(Item item){
        stock.add(item);
        return item;
    }

    public boolean isItemInStock(PostItemDTO postItemDTO){
        return stock.stream()
                .anyMatch(item -> item.getName().equals(postItemDTO.getName())
                && item.getDescription().equals(postItemDTO.getDescription())
                && item.getPrice() == postItemDTO.getPrice());
    }

    public Item getAnItemByProperties(PostItemDTO postItemDTO){
        return stock.stream()
                .filter(item -> item.getName().equals(postItemDTO.getName())
                        && item.getDescription().equals(postItemDTO.getDescription())
                        && item.getPrice() == postItemDTO.getPrice())
                .findFirst()
                .orElseThrow();
    }
}
