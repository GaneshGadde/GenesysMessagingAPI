package com.genesys.messagingservice.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.genesys.messagingservice.entity.User;

public class UserUpdateRequest extends UserRequest{
    @NotNull
    @Positive
    private Long id;

    private String username;

    private String password;

    private String email;

    public User toUser(){
        User user = super.toUser();
        user.setId(this.id);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "id=" + id +
                '}';
    }
}
