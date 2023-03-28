package com.switchfully.eurder.user.api.userDTO;

import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

public class CustomerDTO {
    private final Name name;
    private final ContactInformation contactInformation;

    public CustomerDTO(Name name, ContactInformation contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
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
}
