package com.trabalho.api.dto;

import java.util.Collection;
import java.util.ArrayList;

import com.trabalho.api.model.Empresa;

import lombok.Data;

@Data
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String razaoSocial;
    private boolean ativo;
    private EnderecoDTO endereco;

    public static EmpresaDTO convert(Empresa empresa){
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(empresa.getId());
        empresaDTO.setNome(empresa.getNome());
        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
        empresaDTO.setAtivo(empresa.isAtivo());
        empresaDTO.setEndereco(EnderecoDTO.convert(empresa.getEndereco()));
        return empresaDTO;
    }

    public static Collection<EmpresaDTO> convert(Collection<Empresa> empresas){
        Collection<EmpresaDTO> empresasDTO = new ArrayList<>();
        empresas.forEach(e -> {
            empresasDTO.add(convert(e));
        });
        return empresasDTO;
    }
}
