package com.switchfully.eurder.user.api.dto;

import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.roles.Role;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;

public class CustomerDTO {
    private final Name name;
    private final ContactInformation contactInformation;
    private Role role;

    public CustomerDTO(Name name, ContactInformation contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.role = Role.CUSTOMER;
    }

    public CustomerDTO(User toCopy){
        this.name = toCopy.getName();
        this.contactInformation = toCopy.getContactInformation();
    }

    public Name getName() {
        return name;
    }
    public ContactInformation getContactInformation() {
        return contactInformation;
    }
    public Role getRole() {
        return role;
    }
}
