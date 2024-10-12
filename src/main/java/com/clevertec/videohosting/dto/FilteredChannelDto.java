package com.clevertec.videohosting.dto;

import com.clevertec.videohosting.model.enums.Category;
import com.clevertec.videohosting.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteredChannelDto {
    private String name;
    private Long subscriberCount;
    private Language mainLanguage;
    private String base64Image;
    private Category category;
}
