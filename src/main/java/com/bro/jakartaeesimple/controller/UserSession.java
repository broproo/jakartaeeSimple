package com.bro.jakartaeesimple.controller;

import com.bro.jakartaeesimple.model.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {
    
    private Users currentUser;

    public Users getCurrentUser() { return currentUser; }
    public void setCurrentUser(Users currentUser) { this.currentUser = currentUser; }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}