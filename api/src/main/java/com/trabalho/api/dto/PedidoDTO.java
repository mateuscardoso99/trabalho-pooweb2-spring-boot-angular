package com.trabalho.api.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.trabalho.api.model.Pedido;
import com.trabalho.api.model.Pedido.StatusPedido;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private String descricao;
    private StatusPedido statusPedido;
    private LocalDateTime dataHora;
    private String estabelecimento;
    private String nomeCliente;
    private String emailCliente;

    public static PedidoDTO convert(Pedido pedido){
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setDescricao(pedido.getDescricao());
        pedidoDTO.setStatusPedido(pedido.getStatusPedido());
        pedidoDTO.setDataHora(pedido.getDataHora());
        pedidoDTO.setEstabelecimento(pedido.getEstabelecimento().getNome());
        pedidoDTO.setNomeCliente(pedido.getCliente().getNome());
        pedidoDTO.setEmailCliente(pedido.getCliente().getEmail());
        return pedidoDTO;
    }

    public static Collection<PedidoDTO> convert(Collection<Pedido> pedidos){
        Collection<PedidoDTO> pedidosDTO = new ArrayList<>();
        pedidos.forEach(e -> {
            pedidosDTO.add(convert(e));
        });
        return pedidosDTO;
    }
}
