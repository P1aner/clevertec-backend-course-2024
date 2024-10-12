package com.clevertec.videohosting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppUserDto {
    private String nickname;
    private String name;
    private String email;
}
