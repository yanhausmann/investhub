package com.yanhausmann.investhub.dto;

import com.yanhausmann.investhub.entity.User;

import java.util.List;
import java.util.UUID;

public class UserWithAccountsDTO {
    private UUID userId;
    private String username;
    private String email;
    private List<AccountResponseDTO> accounts;

    public UserWithAccountsDTO(User user, List<AccountResponseDTO> accounts) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.accounts = accounts;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AccountResponseDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }
}

