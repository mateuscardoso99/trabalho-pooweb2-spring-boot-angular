package com.trabalho.api.model;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;
    
    @Column(name = "razaosocial")
    private String razaoSocial;

    @Column
    private String horarioFuncionamento;

    @Column
    private boolean ativo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    //@JsonBackReference // será omitido da serialização pra evitar loop infinito
    private Empresa empresa;

    @OneToMany(mappedBy = "estabelecimento",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Pedido> pedidos;

    public Estabelecimento() {}

    @Builder
    public Estabelecimento(Long id, String nome, String razaoSocial, String horarioFuncionamento, boolean ativo, Endereco endereco,
        Empresa empresa, Collection<Pedido> pedidos) {
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.horarioFuncionamento = horarioFuncionamento;
        this.ativo = ativo;
        this.endereco = endereco;
        this.empresa = empresa;
        this.pedidos = pedidos;
    }
}
