package com.switchfully.eurder.user.api.userDTO;

import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Role;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;

public class UserDTO {

    private final int userID;
    private final Name name;
    private final ContactInformation contactInformation;
    private final Role role;

    public UserDTO(int userID, Name name, ContactInformation contactInformation, Role role) {
        this.userID = userID;
        this.name = name;
        this.contactInformation = contactInformation;
        this.role = role;
    }

    public UserDTO(User userToCopy){
        this.userID = userToCopy.getUserID();
        this.name = userToCopy.getName();
        this.contactInformation = userToCopy.getContactInformation();
        this.role = userToCopy.getRole();
    }

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
}
