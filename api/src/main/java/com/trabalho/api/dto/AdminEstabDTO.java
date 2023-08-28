package com.trabalho.api.dto;

import com.trabalho.api.model.AdminEstab;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Estabelecimento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminEstabDTO extends UsuarioDTO{
    private EstabelecimentoDTO estabelecimento;

    public AdminEstabDTO(){}

    public AdminEstabDTO(UsuarioDTO usuarioDTO, Estabelecimento estab){
        this.id = usuarioDTO.id;
        this.email = usuarioDTO.email;
        this.endereco = usuarioDTO.endereco;
        this.nome = usuarioDTO.nome;
        this.permissoes = usuarioDTO.permissoes;
        this.estabelecimento = null;
    }

    public static AdminEstabDTO convert(AdminEstab adminEstab){
        AdminEstabDTO adminDTO = new AdminEstabDTO();
        adminDTO.setId(adminEstab.getId());
        adminDTO.setEmail(adminEstab.getEmail());
        adminDTO.setNome(adminEstab.getNome());
        adminDTO.setPermissoes(adminEstab.getPermissoes());
        adminDTO.setEndereco(EnderecoDTO.convert(adminEstab.getEndereco()));
        //adminDTO.setEstabelecimento(EstabelecimentoDTO.convert(adminEstab.getEstabelecimento()));
        return adminDTO;
    }
}
