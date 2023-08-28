package com.trabalho.api.dto;

import java.util.Collection;

import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Pedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminEmpresaDTO extends UsuarioDTO{
    private EmpresaDTO empresa;

    public AdminEmpresaDTO(){}

    public AdminEmpresaDTO(UsuarioDTO usuarioDTO, Empresa empresa){
        this.id = usuarioDTO.id;
        this.email = usuarioDTO.email;
        this.endereco = usuarioDTO.endereco;
        this.nome = usuarioDTO.nome;
        this.permissoes = usuarioDTO.permissoes;
        this.empresa = null;
    }

    public static AdminEmpresaDTO convert(AdminEmpresa adminEmpresa){
        AdminEmpresaDTO adminDTO = new AdminEmpresaDTO();
        adminDTO.setId(adminEmpresa.getId());
        adminDTO.setEmail(adminEmpresa.getEmail());
        adminDTO.setNome(adminEmpresa.getNome());
        adminDTO.setPermissoes(adminEmpresa.getPermissoes());
        adminDTO.setEndereco(EnderecoDTO.convert(adminEmpresa.getEndereco()));
        //adminDTO.setEmpresa(EmpresaDTO.convert(adminEmpresa.getEmpresa()));
        return adminDTO;
    }
}
