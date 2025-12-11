package com.fran.proyectomodulo4.userapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email o correo debe tener un formato correcto")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @NotBlank(message = "El password no puede estar vacio")
    @Column(name = "password", unique = true, nullable = false)
    private String password;
    @NotBlank(message = "El rol no puede estar vacio")
    @Column(name = "role", unique = true, nullable = false)
    private String role;
}
