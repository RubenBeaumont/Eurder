package com.switchfully.eurder.user.api.dto;

import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.roles.Role;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;

public class UserDTO {

    private final Name name;
    private final ContactInformation contactInformation;
    private final Role role;

    public UserDTO(Name name, ContactInformation contactInformation, Role role) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.role = role;
    }

    public UserDTO(User userToCopy){
        this.name = userToCopy.getName();
        this.contactInformation = userToCopy.getContactInformation();
        this.role = userToCopy.getRole();
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
