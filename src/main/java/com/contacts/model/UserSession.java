package com.contacts.model;

import javax.persistence.*;

/**
 * Created by Alexander Vashurin 07.02.2017
 */
@Entity
@Table(name = "user_session", uniqueConstraints = {
        @UniqueConstraint(columnNames = "session_id")
})
public class UserSession {
    private Integer sessionId;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", unique = true, nullable = false)
    public Integer getSessionId() {
        return sessionId;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public UserSession() {}

    public UserSession(Integer sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
