package com.switchfully.eurder.user.domain.userObject.roles;

import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

public class Admin extends User {
    public Admin(Name name, ContactInformation contactInformation, String password) {
        super(name, contactInformation, password, Role.ADMIN);
    }

    @Override
    public UserDTO toDTO(){
        return new UserDTO(this);
    }
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
