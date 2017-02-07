package com.contacts.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Vashurin 07.02.2017
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "LOGIN")
})
public class User {
    private Integer userId;
    private String login;
    private String password;
    private String fullName;
    private List<Contact> contacts = new ArrayList<>();
    private List<UserSession> sessions = new ArrayList<>();

    public User() {
        login = "";
        password = "";
        fullName = "";
    }

    public User(String login, String password, String fullName) {
        this();
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    public Integer getUserId() {
        return userId;
    }

    @Column(name = "LOGIN", unique = true, nullable = false)
    public String getLogin() {
        return login;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "FULL_NAME", nullable = false)
    public String getFullName() {
        return fullName;
    }

    @OneToMany(mappedBy = "user")
    public List<Contact> getContacts() {
        return contacts;
    }

    @OneToMany(mappedBy = "user")
    public List<UserSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<UserSession> sessions) {
        this.sessions = sessions;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
