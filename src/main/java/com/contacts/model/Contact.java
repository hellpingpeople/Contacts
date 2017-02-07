package com.contacts.model;

import javax.persistence.*;

/**
 * Created by Alexander Vashurin 07.02.2017
 */
@Entity
@Table(name = "contacts")
public class Contact {
    private String firstName;
    private String lastName;
    private String middleName;
    private String mobileNumber;
    private String homeNumber;
    private String address;
    private String email;
    private Integer id;

    private User user;

    public Contact() {
        firstName = "";
        lastName = "";
        middleName = "";
        mobileNumber = "";
        homeNumber = "";
        address = "";
        email = "";
    }

    public Contact(String firstName, String lastName, String middleName, String mobileNumber, String homeNumber, String address, String email) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.mobileNumber = mobileNumber;
        this.homeNumber = homeNumber;
        this.address = address;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "FIRST_NAME", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LAST_NAME", nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "MIDDLE_NAME", nullable = false)
    public String getMiddleName() {
        return middleName;
    }

    @Column(name = "MOBILE_NUMBER", nullable = false)
    public String getMobileNumber() {
        return mobileNumber;
    }

    @Column(name = "HOME_NUMBER")
    public String getHomeNumber() {
        if (homeNumber == null)
            return "";

        return homeNumber;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        if (address == null)
            return "";

        return address;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        if (email == null)
            return "";

        return email;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", user=" + user.getLogin() +
                '}';
    }
}
