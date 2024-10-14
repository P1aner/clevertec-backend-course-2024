package com.clevertec.videohosting.dto;

import com.clevertec.videohosting.model.enums.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.clevertec.videohosting.dto.ChannelDto.DATA_IMAGE;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChannelDto {
    @Schema(description = "Name of channel", example = "Zoo")
    private String name;
    @Schema(description = "Description of channel", example = "Channel about animal")
    private String description;
    @Schema(description = "Author of channel id", example = "1")
    private Long authorId;
    @Schema(description = "Channel language", example = "1")
    private Language mainLanguage;
    @Schema(description = "Image of channel (base64)", example = DATA_IMAGE)
    private String base64Image;
    @Schema(description = "Category", example = "Animal")
    private String categoryName;
}
