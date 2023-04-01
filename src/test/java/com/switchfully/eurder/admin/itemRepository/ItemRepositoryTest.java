package com.switchfully.eurder.admin.itemRepository;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();
    private static final Item television =
            new Item("Television", "60 inches flat screen", 1199.99, 5);
    private static final ItemDTO updatedTelevision =
            new ItemDTO(1, "Television", "60 inches flat screen", 1199.99, 5);
    private static final PostItemDTO televisionPostDTO =
            new PostItemDTO("Television", "60 inches flat screen", 1199.99, 5);

    @Nested
    @DisplayName("Add an Item")
    class addItem {
        @Test
        void givenAnItem_thenSaveItInTheRepository() {
            itemRepository.addItem(television);

            assertThat(television).isEqualTo(itemRepository.getAnItemByID(television.getItemID()));
        }
    }

//    @Nested
//    @DisplayName("Update an Item")
//    class updateItem{
//        @Test
//        void updateAnItem(){
//            itemRepository.addItem(television);
//            itemRepository.updateItem(updatedTelevision);
//
//            assertThat(updatedTelevision.getName()).isEqualTo(itemRepository.getAnItemByID(television.getItemID()).getName());
//            assertThat(updatedTelevision.getDescription()).isEqualTo(itemRepository.getAnItemByID(television.getItemID()).getDescription());
//            assertThat(updatedTelevision.getAmount()).isEqualTo(itemRepository.getAnItemByID(television.getItemID()).getAmount());
//            assertThat(updatedTelevision.getPrice()).isEqualTo(itemRepository.getAnItemByID(television.getItemID()).getPrice());
//        }
//    }

    @Nested
    @DisplayName("Is the Item in stock")
    class isItemInStock {
        @Test
        void givenAnItem_thenCheckTheRepository_ifPresentReturnsTrue() {
            itemRepository.addItem(television);

            assertThat(itemRepository.isItemInStock(televisionPostDTO)).isTrue();
        }

        @Test
        void givenAnItem_thenCheckTheRepository_ifNotPresentReturnsFalse() {
            assertThat(itemRepository.isItemInStock(televisionPostDTO)).isFalse();
        }
    }

    @Nested
    @DisplayName("Get all Items")
    class getAllItems{
        @Test
        void whenThereAreItemsInStock_returnsAListOfAllItems(){
            itemRepository.addItem(television);
            assertThat(itemRepository.getAllItems()).isNotEmpty().isNotNull();
            assertThat(itemRepository.getAllItems()).containsExactly(television);
        }

        @Test
        void whenThereAreNoItemsInStock_returnsAnEmptyList(){
            assertThat(itemRepository.getAllItems()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Get an Item by ID")
    class getAnItemByID{
        @Test
        void givenAnID_ifAnIDMatch_thenReturnsTheCorrespondingItem(){
            itemRepository.addItem(television);
            assertThat(itemRepository.getAnItemByID(television.getItemID())).isEqualTo(television);
        }

        @Test
        void givenAnID_ifNoIDMatch_thenThrowAnException(){
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> itemRepository.getAnItemByID(1));

            assertEquals("No item was found for id 1.", exception.getMessage());
        }
    }
}
