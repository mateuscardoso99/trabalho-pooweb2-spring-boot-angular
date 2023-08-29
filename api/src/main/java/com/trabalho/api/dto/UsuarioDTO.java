package com.trabalho.api.dto;

import java.util.List;

import com.trabalho.api.model.Permissoes;
import com.trabalho.api.model.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    protected Long id;
    protected String nome;
    protected String email;
    protected EnderecoDTO endereco;
    protected List<Permissoes> permissoes;

    public UsuarioDTO(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.endereco = usuario.getEndereco() != null ? EnderecoDTO.convert(usuario.getEndereco()) : null;
        this.permissoes = usuario.getPermissoes();
    }
}
