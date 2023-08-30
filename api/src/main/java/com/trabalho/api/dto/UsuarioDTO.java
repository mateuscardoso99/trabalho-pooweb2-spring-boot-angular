package com.trabalho.api.dto;

import java.util.List;

import com.trabalho.api.model.Permissoes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    protected Long id;
    protected String nome;
    protected String email;
    protected boolean ativo;
    protected EnderecoDTO endereco;
    protected List<Permissoes> permissoes;
}
