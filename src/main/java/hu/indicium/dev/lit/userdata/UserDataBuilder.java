package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.User;

import java.util.Date;

public class UserDataBuilder implements UserDataBuilderInterface {

    private UserData userData;

    public UserDataBuilder(Long userId) {
        this.userData = new UserData();
    }

    @Override
    public UserDataBuilderInterface setFirstName(String firstName) {
        this.userData.setFirstName(firstName);
        return this;
    }

    @Override
    public UserDataBuilderInterface setLastName(String lastName) {
        this.userData.setLastName(lastName);
        return this;
    }

    @Override
    public UserDataBuilderInterface setGender(Gender gender) {
        this.userData.setGender(gender);
        return this;
    }

    @Override
    public UserDataBuilderInterface setDateOfBirth(Date dateOfBirth) {
        this.userData.setDateOfBirth(dateOfBirth);
        return this;
    }

    @Override
    public UserDataBuilderInterface setStreet(String street) {
        this.userData.setStreet(street);
        return this;
    }

    @Override
    public UserDataBuilderInterface setHouseNumber(String houseNumber) {
        this.userData.setHouseNumber(houseNumber);
        return this;
    }

    @Override
    public UserDataBuilderInterface setZipCode(String zipCode) {
        this.userData.setZipCode(zipCode);
        return this;
    }

    @Override
    public UserDataBuilderInterface setCity(String city) {
        this.userData.setCity(city);
        return this;
    }

    @Override
    public UserDataBuilderInterface setCountry(String country) {
        this.userData.setCountry(country);
        return this;
    }

    @Override
    public UserDataBuilderInterface setEmail(String email) {
        this.userData.setEmail(email);
        return this;
    }

    @Override
    public UserDataBuilderInterface setPhoneNumber(String phoneNumber) {
        this.userData.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public UserDataBuilderInterface setStudentId(int studentId) {
        this.userData.setStudentId(studentId);
        return this;
    }

    @Override
    public UserDataBuilderInterface setUser(User user) {
        this.userData.setUser(user);
        return this;
    }

    @Override
    public UserData build() {
        return this.userData;
    }
}
