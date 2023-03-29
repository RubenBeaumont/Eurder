package com.switchfully.eurder.user.service.mapper;

import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }

    public List<UserDTO> toDTOs(List<User> userList){
        return userList.stream()
                .map(User::toDTO)
                .toList();
    }
}
