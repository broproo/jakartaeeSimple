package com.bro.jakartaeesimple.controller;

import com.bro.jakartaeesimple.model.Users;
import com.bro.jakartaeesimple.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.primefaces.PrimeFaces;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

    private List<Users> usersList;
    private Users selectedUser;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        loadUsers();
    }

    public void loadUsers() {
        usersList = userService.findAll();
    }

    public void openNew() {
        this.selectedUser = new Users();
    }

    public void saveUser() {
        if (this.selectedUser.getUserId() == null) {
            userService.save(this.selectedUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Added successfully"));
        } else {
            userService.save(this.selectedUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Updated successfully"));
        }
        
        loadUsers(); // Refresh the list
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        //PrimeFaces.current().ajax().update(":form:messages", ":form:dt-users");
    }

    public void deleteUser() {
        userService.delete(this.selectedUser);
        this.selectedUser = null;
        loadUsers();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Removed"));
        
    }

    // --- Getters and Setters ---
    
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
}