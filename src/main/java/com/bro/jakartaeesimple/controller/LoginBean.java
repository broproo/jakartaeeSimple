package com.bro.jakartaeesimple.controller;

import com.bro.jakartaeesimple.model.Users;
import com.bro.jakartaeesimple.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    @Inject
    private SecurityContext securityContext;
    @Inject
    private ExternalContext externalContext;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserService userService;
    @Inject
    private UserSession userSession;

    public void login() {
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        UsernamePasswordCredential credential = new UsernamePasswordCredential(email, password);

        AuthenticationStatus status = securityContext.authenticate(
                request,
                response,
                AuthenticationParameters.withParams().credential(credential)
        );

        if (status == AuthenticationStatus.SUCCESS) {
           Users user = userService.findByEmail(email);
           userSession.setCurrentUser(user); // Transfer to session
            // Check if Jakarta Security already handled the redirect
            if (!externalContext.isResponseCommitted()) {
                try {
                    // It didn't, so we redirect to the default dashboard
                    externalContext.redirect(externalContext.getRequestContextPath() + "/app/profile.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Tell JSF that the response is complete and to halt the lifecycle
            facesContext.responseComplete();

        } else if (status == AuthenticationStatus.SEND_FAILURE) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid username or password."));
        }
    }

    // Getters and Setters for email and password...
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
