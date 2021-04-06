package com.genesys.messagingservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Errors extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String username;

    private String errorMessage;

    public static Errors newError(Long userId, String username, String errorMessage) {
        Errors error = new Errors();
        error.setUserId(userId);
        error.setUsername(username);
        error.setErrorMessage(errorMessage);
        return error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
