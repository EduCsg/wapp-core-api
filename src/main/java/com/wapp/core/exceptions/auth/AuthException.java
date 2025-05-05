package com.wapp.core.exceptions.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {
    private final AuthErrorTypeEnum errorType;

    public AuthException(AuthErrorTypeEnum errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public HttpStatus getHttpStatusByType() {
        return switch (errorType) {
            case EMAIL_DUPLICATED -> HttpStatus.CONFLICT;
            case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
        };
    }

    public enum AuthErrorTypeEnum {
        EMAIL_DUPLICATED, //
        INVALID_CREDENTIALS
    }

}
