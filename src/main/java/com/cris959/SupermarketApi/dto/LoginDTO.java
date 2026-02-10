package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "DTO for login credentials")
public class LoginDTO {

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Password", example = "secret123")
    private String password;
}
