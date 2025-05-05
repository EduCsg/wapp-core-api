package com.wapp.core.handlers;

import com.wapp.core.Dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                                                   .map(fieldError -> Map.of(
                                                           "field", fieldError.getField(),
                                                           "error", Objects.requireNonNull(fieldError.getDefaultMessage())
                                                   ))
                                                   .toList();

        ResponseDto response = new ResponseDto("Erro de validação");
        response.setList(errors);

        return ResponseEntity.badRequest().body(response);
    }

}
