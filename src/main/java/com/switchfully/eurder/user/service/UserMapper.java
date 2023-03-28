package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.api.dto.UserDTO;
import com.switchfully.eurder.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }
}
