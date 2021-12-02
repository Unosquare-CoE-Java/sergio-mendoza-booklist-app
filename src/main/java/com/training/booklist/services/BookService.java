package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.BookDto;
import com.training.booklist.dto.CategoryDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService implements Books {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryDao categoryDao;

// for testing purposes
    @Override
    public List<BookEntity> getAllBooks() {
        List<BookEntity> bookEntity = new ArrayList<>();
        bookDao.findAll()
                .forEach(bookEntity::add);
        return bookEntity;
    }

    @Override
    public void saveBook(BookEntity book) {
        bookDao.save(book);
    }

    @Override
    public void updateBook(Long id, BookEntity book) {
        if(bookDao.existsById(id)) {
            book.setId(id);
            bookDao.save(book);
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

    @Override
    public void addCategory(Long categoryid, Long bookid) {
        if(categoryDao.existsById(categoryid) && bookDao.existsById(bookid)) {
            CategoryEntity category = categoryDao.getCategoryEntityById(categoryid);
            BookEntity book = bookDao.getBookEntityById(bookid);
            book.addCategory(category);
            bookDao.save(book);
        }
    }
}
