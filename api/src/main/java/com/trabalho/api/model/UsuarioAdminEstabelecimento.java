package com.trabalho.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adm_estabelecimento")
public class UsuarioAdminEstabelecimento extends Usuario {
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id") 
    private Estabelecimento estabelecimento;
}
