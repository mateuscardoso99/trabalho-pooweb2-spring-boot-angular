package com.trabalho.api.request;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroEstabelecimento (
    @NotBlank(message = "nome inválido")
    @Size(max = 255)
    String nome,

    @NotBlank(message = "razao social inválida")
    @Size(max = 255)
    String razaoSocial,

    @NotNull(message = "horário de funcionamento inválido")
    @DateTimeFormat(pattern = "HH:mm")
    String horarioFuncionamento,

    @NotNull(message = "informe a empresa")
    @Positive(message = "empresa inválida")
    Long idEmpresa,

    @Valid
    CadastroEndereco endereco
){}
