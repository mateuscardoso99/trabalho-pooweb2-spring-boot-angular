package com.trabalho.api.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Pedido;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO extends UsuarioDTO{
    private Collection<PedidoDTO> pedidos;

    public ClienteDTO(UsuarioDTO usuarioDTO, Collection<Pedido> pedidos){
        this.id = usuarioDTO.id;
        this.email = usuarioDTO.email;
        this.endereco = usuarioDTO.endereco;
        this.nome = usuarioDTO.nome;
        this.ativo = usuarioDTO.ativo;
        this.permissoes = usuarioDTO.permissoes;
        this.pedidos = PedidoDTO.convert(pedidos);
    }

    public static ClienteDTO convert(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setAtivo(cliente.isAtivo());
        clienteDTO.setPermissoes(cliente.getPermissoes());
        clienteDTO.setPedidos(PedidoDTO.convert(cliente.getPedidos()));
        clienteDTO.setEndereco(cliente.getEndereco() != null ? EnderecoDTO.convert(cliente.getEndereco()) : null);
        return clienteDTO;
    }

    public static Collection<ClienteDTO> convert(Collection<Cliente> clientes){
        Collection<ClienteDTO> clienteDTOs = new ArrayList<>();
        clientes.forEach(c -> {
            clienteDTOs.add(convert(c));
        });
        return clienteDTOs;
    }
}
