package com.training.booklist.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto {
    public String name;
    public String description;
    public String publisher;
    public String author;
    public String isbn;
    public LocalDate publishedDate;
}
