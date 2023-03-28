package com.switchfully.eurder.user.domain.roles;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;

public class Customer extends User {
    public Customer(Name name, ContactInformation contactInformation) {
        super(name, contactInformation, Role.CUSTOMER);
    }

    public Customer(CustomerDTO customerDTO){
        super(customerDTO, Role.CUSTOMER);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
