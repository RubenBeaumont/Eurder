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
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public AdminService(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper, UserMapper userMapper) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
    }

    public ItemDTO addItem(PostItemDTO postItemDTO){
        Item newItem = new Item(postItemDTO);
        return itemMapper.toDTO(itemRepository.addItem(newItem));
    }

    public List<UserDTO> getAllCustomers(){
        return userMapper.toDTOs(userRepository.getAllCustomers());
    }

    public UserDTO getACustomer(int id){
        return userMapper.toDTO(userRepository.getAUserByID(id));
    }
}