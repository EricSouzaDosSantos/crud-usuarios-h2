package com.senai.crud_spring.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;


public record UsuariosDTO(String idAcesso, String nome, String telefone, String email,
                          @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) MultipartFile foto) {


}
