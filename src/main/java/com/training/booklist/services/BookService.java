package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dto.BookDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService implements Books {
    @Autowired
    private BookDao bookDao;

// for testing purposes
    @Override
    public List<BookEntity> getAllBooks() {
        List<BookEntity> bookEntity = new ArrayList<>();
        bookDao.findAll()
                .forEach(bookEntity::add);
        return bookEntity;
    }

    @Override
    public void saveBook(BookDto book) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setDescription(book.getDescription());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setName(book.getName());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setPublishedDate(book.getPublishedDate());

        bookDao.save(bookEntity);
    }

    @Override
    public void updateBook(Long id, BookDto book) {
        BookEntity bookEntity = new BookEntity();

        if(bookDao.existsById(id)) {
            bookEntity.setId(id);
            bookEntity.setDescription(book.getDescription());
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setIsbn(book.getIsbn());
            bookEntity.setName(book.getName());
            bookEntity.setPublisher(book.getPublisher());
            bookEntity.setPublishedDate(book.getPublishedDate());

            bookDao.save(bookEntity);
        } else {
            throw new BadRequestException("Non existent book");
        }
    }

    @Override
    public void deleteBook(Long id) {
        if(bookDao.existsById(id)){
            bookDao.deleteById(id);
        } else {
            throw new BadRequestException("Book already deleted");
        }
    }
}
