package com.fran.proyectomodulo4.userapi.exception;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id){
        super("Usuario con el ID " + id + " no encontrado");
    }

}
