package com.switchfully.eurder.user.domain.userDetails;

import java.util.Objects;

public record Address(String streetName, int streetNumber, String postalCode, String city) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && Objects.equals(streetName, address.streetName) && Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city);
    }

}
