package com.fran.proyectomodulo4.userapi.controller;

import com.fran.proyectomodulo4.userapi.DTO.LoginRequestDTO;
import com.fran.proyectomodulo4.userapi.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
// el metodo login() es donde vamos a recibir a nuestro usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
/* Se realiza la creacion de un token de intento
pensemos que es como llenar un formulario de vista para el usuario con los datos que mando el mismo, es por eso que contamos con
getEmail() y getPassword()
y de hecho en este punto aun no sabemos si son reales */

        /**Ahora la prueba definitiva
         el manager toma el formulario y verifica con la base de datos, usando la clase UsuarioService
         y si en dado caso la contraseña no coincide (explota y lanza un error 500 BadCredentialsException)*/
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        /** Si en dado caso avanza a este paso significa que el usuario es real y la contraseña es correcta
          y obtenemos los detalles del usuario oficial ya autenticado es decir el resultado es de un tipo especifico como en este caso
         userDetails para acceder a los datos del usuario */
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        /*Generamos el brazalete (JWT) para que no tenga que loguearse de nuevo pronto*/
        String token = jwtUtil.generateToken(userDetails);

        //le devolvemos el token al cliente
        return ResponseEntity.ok("Bearer " + token);
    }
}
