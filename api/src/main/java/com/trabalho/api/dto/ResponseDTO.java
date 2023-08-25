package com.trabalho.api.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private Boolean success;
    private String message;
    private Map<String, List<String>> errors;

    public static <T> ResponseDTO<T> build(T data, Boolean success, String msg, Map<String, List<String>> errors){
        return new ResponseDTO<T>(data, success, msg, errors);
    }
}
