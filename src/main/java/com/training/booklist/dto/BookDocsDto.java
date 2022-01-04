package com.training.booklist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// This class is intended to be used as a sublist of BookSearchApiResultDto
// to simplify retrieving isbn's to used in Open Library's cover Api
public class BookDocsDto {
    private String title;
    private List<String> isbn;
}
