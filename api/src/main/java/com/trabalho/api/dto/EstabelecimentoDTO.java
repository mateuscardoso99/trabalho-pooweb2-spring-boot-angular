package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.Estabelecimento;

import lombok.Data;

@Data
public class EstabelecimentoDTO {
    private Long id;
    private String nome;
    private String razaoSocial;
    private String horarioFuncionamento;
    private boolean ativo;
    private EnderecoDTO endereco;
    private EmpresaDTO empresa;

    public static EstabelecimentoDTO convert(Estabelecimento estabelecimento){
        EstabelecimentoDTO estabelecimentoDTO = new EstabelecimentoDTO();
        estabelecimentoDTO.setId(estabelecimento.getId());
        estabelecimentoDTO.setNome(estabelecimento.getNome());
        estabelecimentoDTO.setRazaoSocial(estabelecimento.getRazaoSocial());
        estabelecimentoDTO.setHorarioFuncionamento(estabelecimento.getHorarioFuncionamento());
        estabelecimentoDTO.setAtivo(estabelecimento.isAtivo());
        estabelecimentoDTO.setEndereco(EnderecoDTO.convert(estabelecimento.getEndereco()));
        estabelecimentoDTO.setEmpresa(EmpresaDTO.convert(estabelecimento.getEmpresa()));
        return estabelecimentoDTO;
    }

    public static Collection<EstabelecimentoDTO> convert(Collection<Estabelecimento> estabs){
        Collection<EstabelecimentoDTO> estabelecimentoDTOs = new ArrayList<>();
        estabs.forEach(e -> {
            estabelecimentoDTOs.add(convert(e));
        });
        return estabelecimentoDTOs;
    }
}
