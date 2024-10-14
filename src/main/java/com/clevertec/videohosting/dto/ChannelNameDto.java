package com.clevertec.videohosting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelNameDto {
    @Schema(description = "Name of channel", example = "Zoo")
    private String name;
}
