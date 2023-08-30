package com.trabalho.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "adm_empresa")
public class UsuarioAdminEmpresa extends Usuario{
    @ManyToOne
    @JoinColumn(name = "empresa_id") 
    private Empresa empresa;
}
