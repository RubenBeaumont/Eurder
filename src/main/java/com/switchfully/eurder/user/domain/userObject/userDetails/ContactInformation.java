package com.switchfully.eurder.user.domain.userObject.userDetails;

import java.util.Objects;

public record ContactInformation(Address address, String emailAddress, String phoneNumber) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(emailAddress, that.emailAddress) && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber);
    }
}
