package com.fran.proyectomodulo4.userapi.controller;

import com.fran.proyectomodulo4.userapi.model.Usuario;
import com.fran.proyectomodulo4.userapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Capa controller para el manejo de resultados en formato JSON
@RestController
//ruta asignada para los resultados http
@RequestMapping("/api/users")
public class UsuarioController {
    //se inicializa la inyeccion de dependencias del servicio
    private final UsuarioService usuarioService;

    //se inicia el servicio
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    //se asigna la ruta para el post encargado de guardar los elementos de nuestro modelo en formato json en la DB
    @PostMapping("POST")
    public ResponseEntity<Usuario> postUser(@Valid @RequestBody Usuario usuario){
        Usuario usuarioCreado = usuarioService.saveUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    //se asigna la ruta para la obtención de la informacion general de los usuarios en la DB
    @GetMapping("/GET")
    public ResponseEntity<List<Usuario>> getUsers(){
         return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getListUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id){
        Usuario usuario = usuarioService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> putUserById(@PathVariable Long id, @Valid @RequestBody Usuario usuario){
        Usuario usuarioActualizado = usuarioService.updateUserById(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        usuarioService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
