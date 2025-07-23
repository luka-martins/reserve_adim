package br.com.lucas.reserve_adim.domain.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(

        @NotNull
        @NotEmpty
        String email,

        @NotNull
        @NotEmpty
        String name,

        @NotNull
        @NotEmpty
        String password,

        UserRole role) {
}
