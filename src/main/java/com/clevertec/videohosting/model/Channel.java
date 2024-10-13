package com.clevertec.videohosting.model;


import com.clevertec.videohosting.model.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;
    @ManyToMany
    private Set<AppUser> subscribers;
    private LocalDateTime createdAt;
    private Language mainLanguage;
    private String base64Image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
