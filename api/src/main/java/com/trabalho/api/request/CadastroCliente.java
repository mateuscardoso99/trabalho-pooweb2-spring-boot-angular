package com.trabalho.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroCliente(
    @NotBlank(message = "nome inválido")
    @Size(min = 4, max = 255, message = "nome entre 4 caracteres e 255 caracteres")
    String nome,

    @Email(message = "email inválido")
    @Size(min = 4, max = 255, message = "email entre 10 caracteres e 255 caracteres")
    String email,

    @NotBlank(message = "informe a senha")
    @Size(min = 4, max = 255, message = "senha minimo 4 caracteres e maximo 255 caracteres")
    String senha,

    @Valid //valida se existe
    CadastroEndereco endereco
){}
