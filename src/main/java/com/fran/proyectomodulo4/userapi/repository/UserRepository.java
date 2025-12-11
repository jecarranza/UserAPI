package com.fran.proyectomodulo4.userapi.repository;

import com.fran.proyectomodulo4.userapi.model.Usuario;
import com.fran.proyectomodulo4.userapi.service.UsuarioService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
