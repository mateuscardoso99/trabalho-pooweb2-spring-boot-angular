package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.UsuarioAdminEmpresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAdminEmpresaDTO extends UsuarioDTO{
    private Empresa empresa;

    public static UsuarioAdminEmpresaDTO convert(UsuarioAdminEmpresa adminEmpresa){
        UsuarioAdminEmpresaDTO adminDTO = new UsuarioAdminEmpresaDTO();
        adminDTO.setId(adminEmpresa.getId());
        adminDTO.setEmail(adminEmpresa.getEmail());
        adminDTO.setNome(adminEmpresa.getNome());
        adminDTO.setAtivo(adminEmpresa.isAtivo());
        adminDTO.setPermissoes(adminEmpresa.getPermissoes());
        adminDTO.setEndereco(adminEmpresa.getEndereco() != null ? EnderecoDTO.convert(adminEmpresa.getEndereco()) : null);
        adminDTO.setEmpresa(new Empresa(adminEmpresa.getEmpresa().getId(), adminEmpresa.getEmpresa().getNome()));
        return adminDTO;
    }

    public static Collection<UsuarioAdminEmpresaDTO> convert(Collection<UsuarioAdminEmpresa> usuarios){
        Collection<UsuarioAdminEmpresaDTO> uAdminEmpresaDTOs = new ArrayList<>();
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
