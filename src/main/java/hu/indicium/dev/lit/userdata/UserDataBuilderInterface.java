package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.User;

import java.util.Date;

public interface UserDataBuilderInterface {
    UserDataBuilderInterface setFirstName(String firstName);

    UserDataBuilderInterface setLastName(String lastName);

    UserDataBuilderInterface setGender(Gender gender);

    UserDataBuilderInterface setDateOfBirth(Date dateOfBirth);

    UserDataBuilderInterface setStreet(String street);

    UserDataBuilderInterface setHouseNumber(String houseNumber);

    UserDataBuilderInterface setZipCode(String zipCode);

    UserDataBuilderInterface setCity(String city);

    UserDataBuilderInterface setCountry(String country);

    UserDataBuilderInterface setEmail(String email);

    UserDataBuilderInterface setPhoneNumber(String phoneNumber);

    UserDataBuilderInterface setStudentId(int studentId);

    UserDataBuilderInterface setUser(User user);

    UserData build();
}
