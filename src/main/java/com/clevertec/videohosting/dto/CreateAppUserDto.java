package com.clevertec.videohosting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppUserDto {
    @Schema(description = "Nickname", example = "Alexis")
    private String nickname;
    @Schema(description = "Name", example = "Alexander")
    private String name;
    @Schema(description = "Email", example = "alex@example.m")
    private String email;
}
