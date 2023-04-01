package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.dto.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.service.exceptions.InvalidParametersException;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import com.switchfully.eurder.user.service.security.Feature;
import com.switchfully.eurder.user.service.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    public AdminService(UserRepository userRepository, ItemRepository itemRepository, UserMapper userMapper, ItemMapper itemMapper) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userMapper = userMapper;
        this.itemMapper = itemMapper;
    }

    public List<UserDTO> getAllCustomers(){
        return userMapper.toDTOs(userRepository.getAllCustomers());
    }

    public UserDTO getACustomer(int id){
        return userMapper.toDTO(userRepository.getAUserByID(id));
    }

    public ItemDTO updateItem(ItemDTO itemDTO){
        return itemMapper.toDTO(itemRepository.updateItem(itemDTO));
    }

    public ItemDTO addItem(PostItemDTO postItemDTO){
        checkForDetailsValidity(postItemDTO);
        if(itemRepository.isItemInStock(postItemDTO)){
            itemRepository.getAnItemByProperties(postItemDTO).setAmount(itemRepository.getAnItemByProperties(postItemDTO).getAmount() + postItemDTO.getAmount());
            return itemMapper.toDTO(itemRepository.getAnItemByProperties(postItemDTO));
        }
        Item newItem = new Item(postItemDTO);
        return itemMapper.toDTO(itemRepository.addItem(newItem));
    }

    private void checkForDetailsValidity(PostItemDTO postItemDTO) {
        if(!isItemDetailsValid(postItemDTO)){
            throw new InvalidParametersException("Input is invalid, a field is empty.");
        }
    }

    public boolean isItemDetailsValid(PostItemDTO postItemDTO){
        return isNameValid(postItemDTO.getName())
                && isDescriptionValid(postItemDTO.getDescription())
                && isPriceValid(postItemDTO.getPrice())
                && isAmountValid(postItemDTO.getAmount());
    }
    private boolean isNameValid(String name){
        return name != null;
    }
    private boolean isDescriptionValid(String description){
        return description != null;
    }
    private boolean isPriceValid(double price){
        return price != 0;
    }
    private boolean isAmountValid(int amount){
        return amount > 0;
    }
}