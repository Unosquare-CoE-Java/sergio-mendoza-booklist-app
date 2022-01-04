package com.training.booklist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// This class is for parsing the results of OpenLibrary's search by title API
public class BookSearchApiResultDto {
    private int numFound;
    private int start;
    private boolean numFoundExact;
    private List<BookDocsDto> docs;
}
