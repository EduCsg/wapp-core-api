package com.wapp.core.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.ToString;

public record RegisterRequestDto(

        @NotBlank(message = "Nome é obrigatório") //
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres") //
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços") //
        String name,

        @Email(message = "E-Mail inválido")  //
        @NotBlank(message = "E-Mail é obrigatório") //
        @Size(max = 100, message = "E-Mail deve ter no máximo 100 caracteres")  //
        @Pattern(regexp = "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$", message = "E-Mail deve conter um domínio válido")  //
        String email,

        @NotBlank(message = "Senha é obrigatória")  //
        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")  //
        @ToString.Exclude  //
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  //
        String password //

) {}
