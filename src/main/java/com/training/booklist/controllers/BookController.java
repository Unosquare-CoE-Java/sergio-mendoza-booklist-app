package com.training.booklist.controllers;

import com.training.booklist.dto.BookDto;
import com.training.booklist.entities.BookEntity;
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
    public List<BookEntity> getAllBooks() {
        return books.getAllBooks();
    }

    @RequestMapping(method = RequestMethod.POST, value="/books")
    @PostMapping(
            value = "/saveBook",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveBook(@RequestBody BookDto book) {
        books.saveBook(book);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/books/{id}")
    public void updateBook(@RequestBody BookDto book, @PathVariable Long id) {
        books.updateBook(id, book);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        books.deleteBook(id);
    }
}
