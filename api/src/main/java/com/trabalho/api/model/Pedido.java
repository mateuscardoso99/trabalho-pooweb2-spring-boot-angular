package com.trabalho.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false) 
    @JsonBackReference
    private Estabelecimento estabelecimento;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) 
    private Usuario usuario;

    public enum StatusPedido{
        PENDENTE,
        CANCELADO,
        ENTREGUE
    }
}
