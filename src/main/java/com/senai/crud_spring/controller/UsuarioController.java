package com.senai.crud_spring.controller;

import com.senai.crud_spring.dtos.UsuariosDTO;
import com.senai.crud_spring.model.Usuarios;
import com.senai.crud_spring.service.ImagemService;
import com.senai.crud_spring.service.UsuariosService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuariosService usuarioService;

    private final ImagemService imagemService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarUsuarioPeloId(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPeloId(id));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UsuariosDTO> cadastrarUsuario(
            @RequestParam("idAcesso") String idAcesso,
            @RequestParam("nome") String nome,
            @RequestParam("telefone") String telefone,
            @RequestParam("email") String email,
            @RequestParam("foto") MultipartFile foto) {

        UsuariosDTO usuarioDTO = new UsuariosDTO(idAcesso, nome, telefone, email, foto);
        usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.status(201).body(usuarioDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuariosDTO usuario, @PathVariable Long id) {
        System.out.println(usuario.email());
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
