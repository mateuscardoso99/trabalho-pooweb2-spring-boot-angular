package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.AdminEmpresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminEmpresaDTO extends UsuarioDTO{
    private Empresa empresa;

    public static AdminEmpresaDTO convert(AdminEmpresa adminEmpresa){
        AdminEmpresaDTO adminDTO = new AdminEmpresaDTO();
        adminDTO.setId(adminEmpresa.getId());
        adminDTO.setEmail(adminEmpresa.getEmail());
        adminDTO.setNome(adminEmpresa.getNome());
        adminDTO.setPermissoes(adminEmpresa.getPermissoes());
        adminDTO.setEndereco(EnderecoDTO.convert(adminEmpresa.getEndereco()));
        adminDTO.setEmpresa(new Empresa(adminEmpresa.getEmpresa().getId(), adminEmpresa.getEmpresa().getNome()));
        return adminDTO;
    }

    public static Collection<AdminEmpresaDTO> convert(Collection<AdminEmpresa> usuarios){
        Collection<AdminEmpresaDTO> uAdminEmpresaDTOs = new ArrayList<>();
        usuarios.forEach(e -> {
            uAdminEmpresaDTOs.add(convert(e));
        });
        return uAdminEmpresaDTOs;
    }

    @Data
    @AllArgsConstructor
    private static class Empresa{
        private Long id;
        private String nome;
    }
}
