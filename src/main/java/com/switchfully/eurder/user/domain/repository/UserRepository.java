package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Admin;
import com.switchfully.eurder.user.domain.userObject.roles.Role;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> listOfUsers = new ArrayList<>(List.of(
            new Admin(
                    new Name("Ruben", "Beaumont"),
                    new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "beaumont-rubben@hotmail.fr", "0471/260818"))
    ));


    public User addUser(User user){
        listOfUsers.add(user);
        return user;
    }

    public List<User> getAllCustomers() {
        return listOfUsers.stream()
                .filter(user -> user.getRole().equals(Role.CUSTOMER))
                .toList();
    }

    public User getAUserByID(int id){
        return listOfUsers.stream()
                .filter((user -> user.getUserID() == id))
                .findFirst()
                .orElseThrow();
    }

    public User getAUserByContactInformation(ContactInformation contactInformation){
        return listOfUsers.stream()
                .filter(user -> user.getContactInformation().equals(contactInformation))
                .findFirst()
                .orElseThrow();
    }
}
