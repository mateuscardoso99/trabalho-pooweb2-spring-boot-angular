package com.trabalho.api.dto;

import java.util.Collection;
import java.util.List;

import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.model.Permissoes;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nome;
    private String email;
    private EnderecoDTO endereco;
    private List<Permissoes> permissoes;
    private Collection<Pedido> pedidos;

    public static ClienteDTO convert(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setPermissoes(cliente.getPermissoes());
        return clienteDTO;
    }
}
