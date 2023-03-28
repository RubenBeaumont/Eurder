package com.switchfully.eurder.user.domain.userObject;

import com.switchfully.eurder.user.api.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.roles.Role;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

import java.util.Objects;

public abstract class User {
    private final int userID;
    private static int counter;
    private final Name name;
    private final ContactInformation contactInformation;
    private final Role role;

    public User(Name name, ContactInformation contactInformation, Role role) {
        this.userID = ++counter;
        this.name = name;
        this.contactInformation = contactInformation;
        this.role = role;
    }

    public User(CustomerDTO customerDTO, Role role){
        this.userID = ++counter;
        name = customerDTO.getName();
        contactInformation = customerDTO.getContactInformation();
        this.role = role;
    }


    public abstract UserDTO toDTO();

    public int getUserID() {
        return userID;
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
        return Objects.equals(name, user.name) && Objects.equals(contactInformation, user.contactInformation) && role == user.role;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, contactInformation, role);
    }
}
