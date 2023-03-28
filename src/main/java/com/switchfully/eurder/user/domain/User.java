package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.api.dto.CustomerDTO;
import com.switchfully.eurder.user.domain.roles.Role;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;

import java.util.Objects;

public abstract class User {
    private final Name name;
    private final ContactInformation contactInformation;
    private final Role role;

    public User(Name name, ContactInformation contactInformation, Role role) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.role = role;
    }

    public User(CustomerDTO customerDTO){
        name = customerDTO.getName();
        contactInformation = customerDTO.getContactInformation();
        role = customerDTO.getRole();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(contactInformation, user.contactInformation);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, contactInformation);
    }
}
