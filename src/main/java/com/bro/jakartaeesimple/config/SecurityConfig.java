package com.bro.jakartaeesimple.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:app/ecomm", // Change to your JNDI name
    callerQuery = "select PASSWORD_HASH from USERS where EMAIL = ?",
    groupsQuery = "select USER_ROLE from USERS where EMAIL = ?",
    hashAlgorithm = Pbkdf2PasswordHash.class,
    priority = 10
)
@FacesConfig // Activates CDI bean support for Faces in Jakarta EE
@CustomFormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/login.xhtml",
        errorPage = "", // Left blank intentionally, we handle errors in the LoginBean
        useForwardToLogin = false
    )
)
@ApplicationScoped
public class SecurityConfig {
    // No code needed here, just the annotations
}