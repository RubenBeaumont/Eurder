package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.domain.itemObject.ItemGroup;
import com.switchfully.eurder.user.domain.itemObject.Order;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.OrderRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;

    public CustomerService(OrderRepository orderRepository, UserRepository userRepository, ItemRepository itemRepository, ItemMapper itemMapper, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.orderMapper = orderMapper;
    }

    public List<ItemDTO> getAllItems(){
        return itemMapper.toDTOs(itemRepository.getAllItems());
    }

    public OrderDTO orderAnItem(int customerID, int itemID, int amount){
        isAmountValid(itemID, amount);
        modifyAmountForSelectedItemInStock(itemID, amount);
        ItemGroup itemGroup = createItemGRoup(itemID, amount);
        Order order = new Order(itemGroup);
        return orderMapper.toDTO(orderRepository.createAnOrder(userRepository.getAUserByID(customerID), order));
    }

    private ItemGroup createItemGRoup(int itemID, int amount) {
        ItemDTO itemDTO = new ItemDTO(itemRepository.getAnItemByID(itemID));
        ItemGroup itemGroup = new ItemGroup(itemDTO);
        itemGroup.getItemDTO().setAmount(amount);
        setShippingDateDependingOnStock(itemID, itemGroup);
        return itemGroup;
    }
    private void setShippingDateDependingOnStock(int itemID, ItemGroup itemGroup) {
        if(itemRepository.getAnItemByID(itemID).getAmount() == 0){
            itemGroup.setShippingDate(LocalDate.now().plusWeeks(1));
        }
    }
    private void isAmountValid(int itemID, int amount) {
        if(amount > itemRepository.getAnItemByID(itemID).getAmount()){
            throw new IllegalArgumentException();
        }
    }
    private void modifyAmountForSelectedItemInStock(int itemID, int amount) {
        itemRepository.getAnItemByID(itemID).setAmount(
                itemRepository.getAnItemByID(itemID).getAmount() - amount);
    }
}
