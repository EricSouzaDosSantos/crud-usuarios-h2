package com.senai.crud_spring.service;

import com.senai.crud_spring.dtos.UsuariosDTO;
import com.senai.crud_spring.model.Usuarios;
import com.senai.crud_spring.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuariosService {

    private final UsuarioRepository usuarioRepository;

    private final ImagemService imagemService;

    public void cadastrarUsuario(UsuariosDTO usuarioDTO) {
        try {
            Usuarios usuario = new Usuarios();
            usuario.setNome(usuarioDTO.nome());
            usuario.setIdAcesso(UUID.fromString(usuarioDTO.idAcesso()));
            usuario.setTelefone(usuarioDTO.telefone());
            if (usuarioDTO.foto() != null && !usuarioDTO.foto().isEmpty()) {
                usuario.setCaminhoImagem(imagemService.storeFile(usuarioDTO.foto()));
            }
            usuario.setEmail(usuarioDTO.email());
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizarUsuario(UsuariosDTO usuariosDTO, Long id){
        try {
            Usuarios usuario = buscarUsuarioPeloId(id);
            System.out.println(usuario.getNome());
            usuario.setNome(usuariosDTO.nome());
            usuario.setEmail(usuariosDTO.email());
            usuario.setTelefone(usuariosDTO.telefone());
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletarUsuario(long id){
        usuarioRepository.deleteById(id);
    }

    public Usuarios buscarUsuarioPeloId(long id){
        return usuarioRepository.findById(id).orElseThrow();
    }

    public List<Usuarios> buscarTodosUsuarios(){
        return usuarioRepository.findAll();
    }
}
