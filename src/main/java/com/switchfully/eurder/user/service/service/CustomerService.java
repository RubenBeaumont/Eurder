package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.ItemGroupDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.OrderReviewDTO;
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

    public OrderReviewDTO getAllMyOrders(int customerID){
        OrderReviewDTO orderReviewDTO = new OrderReviewDTO(
                orderMapper.toDTOs(orderRepository.getOrdersByKey(userRepository.getAUserByID(customerID))));
        setTotalPriceOfAllOrders(orderReviewDTO);
        return orderReviewDTO;
    }

    private static void setTotalPriceOfAllOrders(OrderReviewDTO orderReviewDTO) {
        orderReviewDTO.setTotalPrice(orderReviewDTO.getOrderDTOList().stream()
                .mapToDouble(OrderDTO::getPrice).sum());
    }

    public OrderDTO orderAnItem(int customerID, List<ItemGroupDTO> itemGroupDTOList){
        List<ItemGroup> itemGroupList = itemGroupDTOList.stream().map(ItemGroup::new).toList();
        setShippingDate(itemGroupList);
        itemGroupList.forEach(itemGroup -> modifyAmountForSelectedItemInStock(itemGroup.getItemID(), itemGroup.getAmount()));
        itemGroupList.forEach(this::calculatePriceOfItemGroup);
        Order order = new Order(itemGroupList);
        return orderMapper.toDTO(orderRepository.createAnOrder(userRepository.getAUserByID(customerID), order));
    }


    private void setShippingDate(List<ItemGroup> itemGroupList) {
        itemGroupList.forEach(itemGroup -> {
            if(!isItemStockSufficient(itemGroup.getItemID(), itemGroup.getAmount())){
                itemGroup.setShippingDate(LocalDate.now().plusWeeks(1));
            }});
    }
    private boolean isItemStockSufficient(int itemID, int amount) {
        return amount <= itemRepository.getAnItemByID(itemID).getAmount();
    }
    private void modifyAmountForSelectedItemInStock(int itemID, int amount) {
        itemRepository.getAnItemByID(itemID).setAmount(
                itemRepository.getAnItemByID(itemID).getAmount() - amount);
    }
    private void calculatePriceOfItemGroup(ItemGroup itemGroup) {
        itemGroup.setPrice(itemRepository.getAnItemByID(itemGroup.getItemID()).getPrice()*itemGroup.getAmount());
    }
}
