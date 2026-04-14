package com.bro.jakartaeesimple.controller;

import com.bro.jakartaeesimple.model.Users;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UserBeanP implements Serializable {

    private Users currentUser;

    @PostConstruct
    public void init() {
        // In a real app, you would fetch this from the Session via Jakarta Security
        // For now, we instantiate the model you created
        currentUser = new Users();
        currentUser.setFirstName("Ezio");
        currentUser.setLastName("Auditore");
        currentUser.setEmail("ezio@bro-ecom.com");
        currentUser.setUserRole("Developer");
    }

    public void updateProfile() {
        // Logic to call your Facade/EntityManager goes here
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile Updated"));
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }
}