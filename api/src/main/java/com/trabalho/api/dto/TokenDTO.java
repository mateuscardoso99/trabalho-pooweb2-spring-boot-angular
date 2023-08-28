package com.trabalho.api.dto;

import lombok.Data;

@Data
public class TokenDTO<T> {
    private String token;
    private T usuario;
}
