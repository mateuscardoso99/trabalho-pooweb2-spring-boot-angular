package com.trabalho.api.model;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends Usuario {
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL)
    private Collection<Pedido> pedidos;
}
