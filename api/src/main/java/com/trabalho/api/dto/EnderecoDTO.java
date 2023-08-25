package com.trabalho.api.dto;

import com.trabalho.api.model.Endereco;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long id;
    private String cidade;
    private String uf;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;
    private String latitude;
    private String longitude;

    public static EnderecoDTO convert(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLatitude(endereco.getLatitude());
        enderecoDTO.setLongitude(endereco.getLongitude());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setUf(endereco.getUf());
        return enderecoDTO;
    }
}
