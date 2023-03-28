package com.switchfully.eurder.user.domain.repository;

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
}
