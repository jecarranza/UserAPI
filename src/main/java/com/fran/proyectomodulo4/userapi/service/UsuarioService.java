package com.fran.proyectomodulo4.userapi.service;

import com.fran.proyectomodulo4.userapi.exception.UserNotFoundException;
import com.fran.proyectomodulo4.userapi.model.Usuario;
import com.fran.proyectomodulo4.userapi.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
//Capa de servicio de usuarios CRUD
@Service
public class UsuarioService {

    //inicializamos la inyeccion de dependencias
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //se realiza inyeccion
    public UsuarioService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //metodo de guardado de usuario nuevo
    public Usuario saveUser(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }

    //metodo para obtener la lista de usuarios en general
    public List<Usuario> getListUsers(){
        return userRepository.findAll();
    }

    //buscar por id
    public Usuario getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }

    public Usuario updateUserById(Long id, Usuario usuarioNuevo){
        Usuario usuarioExistente = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        usuarioExistente.setNombre(usuarioNuevo.getNombre());
        usuarioExistente.setEmail(usuarioNuevo.getEmail());

        return userRepository.save(usuarioExistente);
    }

    public void deleteUserById(Long id){
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
