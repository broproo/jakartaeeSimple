/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bro.jakartaeesimple.config;

/**
 *
 * @author ezio
 */
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

@LoginToContinue(
    loginPage = "/login.xhtml",
    errorPage = "/login.xhtml?error=true"
)
public class ApplicationConfig {
}