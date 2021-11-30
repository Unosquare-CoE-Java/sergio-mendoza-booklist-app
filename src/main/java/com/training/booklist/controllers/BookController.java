package com.training.booklist.controllers;

import com.training.booklist.dto.BookDto;
import com.training.booklist.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService books;

    @RequestMapping("/books")
    public List<BookDto> getBooks() {
        return books.getBooks();
    }

    @RequestMapping(method = RequestMethod.POST, value="/books")
    @PostMapping(
            value = "/saveBook",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveBook(@RequestBody BookDto book) {
        books.saveBook(book);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/books/{name}")
    public void updateBook(@RequestBody BookDto book, @PathVariable String name) {
        books.updateBook(name, book);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/books/{name}")
    public void deleteBook(@PathVariable String name) {
        books.deleteBook(name);
    }
}
