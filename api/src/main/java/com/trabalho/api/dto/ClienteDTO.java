package com.trabalho.api.dto;

import java.util.Collection;

import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Pedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO extends UsuarioDTO{
    private Collection<Pedido> pedidos;

    public ClienteDTO(){}

    public ClienteDTO(UsuarioDTO usuarioDTO, Collection<Pedido> pedidos){
        this.id = usuarioDTO.id;
        this.email = usuarioDTO.email;
        this.endereco = usuarioDTO.endereco;
        this.nome = usuarioDTO.nome;
        this.permissoes = usuarioDTO.permissoes;
        this.pedidos = pedidos;
    }

    public static ClienteDTO convert(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setPermissoes(cliente.getPermissoes());
        clienteDTO.setPedidos(cliente.getPedidos());
        clienteDTO.setEndereco(EnderecoDTO.convert(cliente.getEndereco()));
        return clienteDTO;
    }
}
