package com.trabalho.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false) 
    @JsonBackReference
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) 
    private Cliente cliente;

    public enum StatusPedido{
        PENDENTE,
        CANCELADO,
        ENTREGUE
    }
}
