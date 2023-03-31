package com.switchfully.eurder.user.api.dto.userDTO;

import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

public class CustomerDTO {
    private final Name name;
    private final ContactInformation contactInformation;
    private final String password;

    public CustomerDTO(Name name, ContactInformation contactInformation, String password) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.password = password;
    }

    public CustomerDTO(User toCopy){
        this.name = toCopy.getName();
        this.contactInformation = toCopy.getContactInformation();
        this.password = toCopy.getPassword();
    }

    public Name getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }
}
