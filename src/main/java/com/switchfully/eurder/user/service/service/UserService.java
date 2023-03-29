package com.switchfully.eurder.user.service.service;

import com.switchfully.eurder.user.api.dto.userDTO.CustomerDTO;
import com.switchfully.eurder.user.api.dto.userDTO.UserDTO;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.roles.Customer;
import com.switchfully.eurder.user.service.exceptions.InvalidParametersException;
import com.switchfully.eurder.user.service.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO registerACustomer(CustomerDTO customerDTO)throws RuntimeException{
        if(!isUserProfileValid(customerDTO)) {
            throw new InvalidParametersException("Input is invalid, either a field is empty or incorrect.");
        }
        if(userRepository.doesUserExist(customerDTO)) {
            throw new InvalidParametersException("Input is invalid, email or phone-number is already in use.");
        }
        User newCustomer = new Customer(customerDTO);
        return userMapper.toDTO(userRepository.addUser(newCustomer));
    }


    public boolean isUserProfileValid(CustomerDTO customerDTO){
        return isFirstNameValid(customerDTO.getName().firstName())
                && isLastNameValid(customerDTO.getName().lastName())
                && isStreetNameValid(customerDTO.getContactInformation().address().streetName())
                && isStreetNumberValid(customerDTO.getContactInformation().address().streetNumber())
                && isPostcodeValid(customerDTO.getContactInformation().address().postalCode())
                && isCityValid(customerDTO.getContactInformation().address().city())
                && isEmailValid(customerDTO.getContactInformation().emailAddress())
                && isPhoneNumberValid(customerDTO.getContactInformation().phoneNumber());
    }
    private boolean isFirstNameValid(String firstName){
        return firstName != null;
    }
    private boolean isLastNameValid(String lastName){
        return lastName != null;
    }
    private boolean isStreetNameValid(String streetName){
        return streetName != null;
    }
    private boolean isStreetNumberValid(int streetNumber){
        return streetNumber != 0;
    }
    private boolean isPostcodeValid(String postalCode) {
        return postalCode.matches("\\d{4}") && postalCode.length() == 4;
    }
    private boolean isCityValid(String city) {
        return city != null && city.matches("[A-Za-z-]+");
    }
    private boolean isEmailValid(String emailAddress) {
        return emailAddress != null && emailAddress.matches(
                "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber.length() == 10 && phoneNumber.startsWith("0");
    }
}
