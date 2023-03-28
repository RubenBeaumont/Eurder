package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.domain.roles.Admin;
import com.switchfully.eurder.user.domain.userDetails.Address;
import com.switchfully.eurder.user.domain.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userDetails.Name;
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

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public User getAUserByContactInformation(ContactInformation contactInformation){
        return listOfUsers.stream()
                .filter(user -> user.getContactInformation().equals(contactInformation))
                .findFirst()
                .orElseThrow();
    }
}
