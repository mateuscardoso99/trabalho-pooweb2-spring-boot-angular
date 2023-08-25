
package com.trabalho.api.model;

import java.util.Collection;
import java.util.List;

import com.trabalho.api.utils.StringListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @Convert(converter = StringListConverter.class)
    @Column(name = "permissoes")
    private List<Permissoes> permissoes;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id") 
    private Estabelecimento vinculoEstabelecimento;

    @ManyToOne
    @JoinColumn(name = "empresa_id") 
    private Empresa vinculoEmpresa;
    
    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private Collection<Pedido> pedidos;
}
