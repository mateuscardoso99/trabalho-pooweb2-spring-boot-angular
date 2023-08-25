package com.trabalho.api.request;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroEstabelecimento {
    @NotBlank(message = "nome inválido")
    @Size(max = 255)
    private String nome;

    @NotBlank(message = "razao social inválida")
    @Size(max = 255)
    private String razaoSocial;

    @NotNull(message = "horário de funcionamento inválido")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horarioFuncionamento;

    @NotNull(message = "informe a empresa")
    @Positive(message = "empresa inválida")
    private Long idEmpresa;

    @Valid
    private CadastroEndereco endereco;
}
