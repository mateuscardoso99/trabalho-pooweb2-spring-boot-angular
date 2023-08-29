package com.trabalho.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroUsuario {
    @NotBlank(message = "nome inválido")
    @Size(min = 4, max = 255, message = "nome entre 4 caracteres e 255 caracteres")
    private String nome;

    @Email(message = "email inválido")
    @Size(min = 4, max = 255, message = "email entre 10 caracteres e 255 caracteres")
    private String email;

    @NotBlank(message = "informe a senha")
    @Size(min = 4, max = 255, message = "senha minimo 4 caracteres e maximo 255 caracteres")
    private String senha;

    private CadastroEndereco endereco;
}
