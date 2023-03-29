package com.switchfully.eurder.admin.itemRepository;

import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();
    private static final Item television =
            new Item("Television", "60 inches flat screen", 1199.99, 5);
    private static final PostItemDTO televisionDTO =
            new PostItemDTO("Television", "60 inches flat screen", 1199.99, 5);

    @Test
    void addItem_givenAnItem_thenSaveItInTheRepository(){
        itemRepository.addItem(television);

        assertThat(television).isEqualTo(itemRepository.getAnItemByProperties(televisionDTO));
    }

    @Test
    void isItemInStock_givenAnItem_thenCheckTheRepository_ifPresentReturnsTrue(){
        itemRepository.addItem(television);

        assertThat(itemRepository.isItemInStock(televisionDTO)).isTrue();
    }

    @Test
    void isItemInStock_givenAnItem_thenCheckTheRepository_ifNotPresentReturnsFalse(){
        assertThat(itemRepository.isItemInStock(televisionDTO)).isFalse();
    }
}
