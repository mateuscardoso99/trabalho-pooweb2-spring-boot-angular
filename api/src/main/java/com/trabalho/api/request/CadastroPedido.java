package com.trabalho.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroPedido {
    @NotBlank
    @Size(max = 255, message = "descrição max 255 caracteres")
    private String descricao;

    @NotNull(message = "informe o estab")
    @Positive(message = "estab inválido")
    private Long idEstabelecimento;
}
