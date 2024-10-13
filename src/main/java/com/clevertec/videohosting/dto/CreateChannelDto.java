package com.clevertec.videohosting.dto;

import com.clevertec.videohosting.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChannelDto {
    private String name;
    private String description;
    private Long authorId;
    private Language mainLanguage;
    private String base64Image;
    private String categoryName;
}
