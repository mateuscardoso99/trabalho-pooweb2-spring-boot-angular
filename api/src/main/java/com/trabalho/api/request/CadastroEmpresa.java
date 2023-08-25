package com.trabalho.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroEmpresa {
    @NotBlank(message = "nome inválido")
    @Size(max = 255)
    private String nome;

    @NotBlank(message = "razao social inválida")
    @Size(max = 255)
    private String razaoSocial;
    
    @Valid
    private CadastroEndereco endereco;
}
