package com.trabalho.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cidade;
    @Column
    private String uf;
    @Column
    private String bairro;
    @Column
    private String rua;
    @Column
    private String numero;
    @Column
    private String complemento;
    @Column
    private String latitude;
    @Column
    private String longitude;
}
