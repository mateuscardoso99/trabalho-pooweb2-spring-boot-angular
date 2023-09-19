package com.trabalho.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroEndereco (
    @NotBlank(message = "cidade inválida")
    @Size(max = 255)
    String cidade,

    @NotBlank(message = "uf inválida")
    @Size(min = 2, max = 2)
    String uf,

    @NotBlank(message = "bairro inválido")
    @Size(max = 255)
    String bairro,

    @NotBlank(message = "rua inválida")
    @Size(max = 255)
    String rua,

    @NotBlank(message = "numero inválido")
    @Size(max = 20)
    String numero,

    @Size(max = 50, message = "tamanho complemento <= 50")
    String complemento,

    @Size(max = 100, message = "tamanho latitude <= 20")
    String latitude,

    @Size(max = 100, message = "tamanho longitude <= 20")
    String longitude    
){}
