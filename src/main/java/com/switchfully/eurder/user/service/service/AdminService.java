package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.itemDTO.ItemDTO;
import com.switchfully.eurder.user.api.itemDTO.PostItemDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import com.switchfully.eurder.user.domain.repository.ItemRepository;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.service.mapper.ItemMapper;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

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

    public ItemDTO addItem(PostItemDTO postItemDTO){
        if(itemRepository.isItemInStock(postItemDTO)){
            itemRepository.getAnItemByProperties(postItemDTO)
                    .setAmount(itemRepository.getAnItemByProperties(postItemDTO).getAmount()
                            + postItemDTO.getAmount());
            return itemMapper.toDTO(itemRepository.getAnItemByProperties(postItemDTO));
        }
        Item newItem = new Item(postItemDTO);
        return itemMapper.toDTO(itemRepository.addItem(newItem));
    }
}