package com.switchfully.eurder.user.domain.repository;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.userObject.roles.Admin;
import com.switchfully.eurder.user.domain.userObject.roles.Role;
import com.switchfully.eurder.user.domain.userObject.userDetails.Address;
import com.switchfully.eurder.user.domain.userObject.userDetails.ContactInformation;
import com.switchfully.eurder.user.domain.userObject.userDetails.Name;
import com.switchfully.eurder.user.service.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> listOfUsers = new ArrayList<>(List.of(
            new Admin(
                    new Name("Ruben", "Beaumont"),
                    new ContactInformation(new Address("Rue Berkendael", 26, "1190", "Forest"), "admin@gmail.be", "0471260818"), "123")
    ));


    public User addUser(User user){
        listOfUsers.add(user);
        return user;
    }

    public boolean doesUserExist(CustomerDTO customerDTO){
        return listOfUsers.stream()
                .anyMatch(user -> user.getContactInformation().phoneNumber().equals(customerDTO.getContactInformation().phoneNumber())
                        || user.getContactInformation().emailAddress().equals(customerDTO.getContactInformation().emailAddress()));
    }

    public List<User> getAllCustomers() {
        return listOfUsers.stream()
                .filter(user -> user.getRole().equals(Role.CUSTOMER))
                .toList();
    }

    public User getAUserByID(int id){
        return listOfUsers.stream()
                .filter((user -> user.getUserID() == id && user.getRole().equals(Role.CUSTOMER)))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No customer was found for id " + id + "."));
    }

    public User getAUserByEmail(String email){
        return listOfUsers.stream()
                .filter(user -> user.getContactInformation().emailAddress().equals(email))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No user found for that e-mail address."));

    }

    public User getAUserByContactInformation(ContactInformation contactInformation){
        return listOfUsers.stream()
                .filter(user -> user.getContactInformation().equals(contactInformation))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No customer was found."));
    }
}
