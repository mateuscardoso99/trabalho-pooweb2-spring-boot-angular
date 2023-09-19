package com.trabalho.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroEmpresa (
    @NotBlank(message = "nome inválido")
    @Size(max = 255)
    String nome,

    @NotBlank(message = "razao social inválida")
    @Size(max = 255)
    String razaoSocial,
    
    @Valid
    CadastroEndereco endereco
){}
