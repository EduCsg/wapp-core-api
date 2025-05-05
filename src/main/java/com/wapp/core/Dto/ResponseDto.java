package com.wapp.core.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private final String id = UUID.randomUUID().toString();
    private final String timestamp = LocalDateTime.now().toString();
    private boolean success;
    private String message;
    private Object data;
    private List<?> list;

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseDto(Object data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

}
