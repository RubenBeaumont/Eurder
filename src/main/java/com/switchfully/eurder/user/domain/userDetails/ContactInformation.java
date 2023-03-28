package com.switchfully.eurder.user.domain.userDetails;

public class ContactInformation {
    private final String emailAddress;
    private final Address address;
    private final String phoneNumber;

    public ContactInformation(Address address, String emailAddress, String phoneNumber) {
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public Address getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
