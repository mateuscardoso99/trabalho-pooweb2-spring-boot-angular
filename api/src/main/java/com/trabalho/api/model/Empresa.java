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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(name = "razaosocial")
    private String razaoSocial;

    @Column
    private boolean ativo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "empresa",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonManagedReference //serializada normalmente.
    private Collection<Estabelecimento> estabelecimentos;

    public Empresa(){}

    @Builder
    public Empresa(Long id, String nome, String razaoSocial, Endereco endereco,
            Collection<Estabelecimento> estabelecimentos) {
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.endereco = endereco;
        this.estabelecimentos = estabelecimentos;
    }    
}
