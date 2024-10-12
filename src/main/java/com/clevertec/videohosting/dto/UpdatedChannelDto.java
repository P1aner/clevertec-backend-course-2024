package com.clevertec.videohosting.dto;

import com.clevertec.videohosting.model.enums.Category;
import com.clevertec.videohosting.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedChannelDto {
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Language mainLanguage;
    private String base64Image;
    private Category category;
}
