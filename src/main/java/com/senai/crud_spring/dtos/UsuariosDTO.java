package com.senai.crud_spring.dtos;

import org.springframework.web.multipart.MultipartFile;


public record UsuariosDTO(String idAcesso, String nome, String telefone, String email) {

}
