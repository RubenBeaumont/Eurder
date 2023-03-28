package com.switchfully.eurder.user.domain.roles;

import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;

public class Admin extends User {
    public Admin(Name name, ContactInformation contactInformation) {
        super(name, contactInformation, Role.ADMIN);
    }
}
