package com.training.booklist.dto;

import com.training.booklist.entities.CategoryEntity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OldBookDto {
    private String name;
    private String description;
    private String author;
    private LocalDate publishedDate;
    private Set<CategoryEntity> categories = new HashSet<>();
}
