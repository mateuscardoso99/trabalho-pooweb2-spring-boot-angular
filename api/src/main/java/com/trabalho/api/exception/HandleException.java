package com.trabalho.api.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.trabalho.api.dto.ResponseDTO;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
        showStackTrace(ex);
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                //.map(FieldError::getDefaultMessage)
                                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                                .collect(Collectors.toList());
        return new ResponseEntity<>(
            ResponseDTO.build(null, false, "Erro ao executar operação", Map.of("errors", errors)),
            new HttpHeaders(), 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        showStackTrace(ex);
        return new ResponseEntity<>(Map.of("errors", List.of("erro ao processar solicitação")), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<Map<String, List<String>>> DataNotFoundException(DataNotFoundException ex) {
        showStackTrace(ex);
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(Map.of("errors", errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        showStackTrace(ex);
        return new ResponseEntity<>(Map.of("errors", Arrays.asList("erro ao processar solicitação")), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Map<String,String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        showStackTrace(ex);
        String erro = "campo " + ex.getName() + " deve ser do tipo " + ex.getRequiredType().getSimpleName();
        return new ResponseEntity<>(Map.of("errors",erro), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ResponseDTO<?>> handleBadRequest(BadRequestException ex){
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, false, null, Map.of("errors",Arrays.asList(ex.getMessage())));
        showStackTrace(ex);
        return new ResponseEntity<ResponseDTO<?>>(responseDTO,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    private void showStackTrace(Exception e){
        e.printStackTrace();
    }
}
