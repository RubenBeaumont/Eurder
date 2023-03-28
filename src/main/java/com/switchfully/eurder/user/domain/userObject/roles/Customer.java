package com.switchfully.eurder.user.domain.userObject.roles;

import com.switchfully.eurder.user.api.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

public class Customer extends User {
    public Customer(Name name, ContactInformation contactInformation) {
        super(name, contactInformation, Role.CUSTOMER);
    }

    public Customer(CustomerDTO customerDTO){
        super(customerDTO, Role.CUSTOMER);
    }

    @Override
    public UserDTO toDTO(){
        return new UserDTO(this);
    }
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
