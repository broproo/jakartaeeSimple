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
        // FIX: Initialize the object so the "Create Account" fields have a target
        this.selectedUser = new Users(); 
    }

    public void loadUsers() {
        try {
            usersList = userService.findAll();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not load users.");
        }
    }

    /**
     * Used for Dialog-based creation (resets the object)
     */
    public void openNew() {
        this.selectedUser = new Users();
    }

    public void saveUser() {
        try {
            if (this.selectedUser.getUserId() == null) {
                userService.save(this.selectedUser);
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Account created successfully");
            } else {
                userService.save(this.selectedUser);
                addMessage(FacesMessage.SEVERITY_INFO, "Updated", "User information updated");
            }

            loadUsers(); // Refresh the list
            
            // If you are using a dialog, hide it. 
            // If you are on a standalone page, you might want to redirect instead.
            if (PrimeFaces.current().isAjaxRequest()) {
                PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            }
            
            // Optional: Reset the object for the next registration if staying on the same page
            this.selectedUser = new Users();

        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not save user: " + e.getMessage());
        }
    }

    public void deleteUser() {
        if (this.selectedUser != null) {
            userService.delete(this.selectedUser);
            addMessage(FacesMessage.SEVERITY_WARN, "Removed", "User has been deleted");
            this.selectedUser = new Users(); // Reset to avoid null pointers
            loadUsers();
        }
    }

    // Helper method to keep code clean
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
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