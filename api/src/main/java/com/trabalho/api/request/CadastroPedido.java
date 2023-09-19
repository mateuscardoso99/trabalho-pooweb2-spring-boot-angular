package com.trabalho.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroPedido (
    @NotBlank
    @Size(max = 255, message = "descrição max 255 caracteres")
    String descricao,

    @NotNull(message = "informe o estab")
    @Positive(message = "estab inválido")
    Long idEstabelecimento
){}
