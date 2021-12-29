package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.OldBookDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookService implements Books {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryService categoryService;

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

    @Override
    public Map<String, Set<String>> getBooksByAuthor() {
        List<BookEntity> books = getAllBooks();
        Map<String, List<BookEntity>> booksFilteredByAuthor = books.stream()
                .collect(Collectors.groupingBy(BookEntity::getAuthor));
    //  Getting the titles
        Map<String, Set<String>> titlesFilteredByAuthor = new HashMap<>();
        booksFilteredByAuthor.forEach((String author, List<BookEntity> bookList) -> {
            titlesFilteredByAuthor.put(author, getNamesFromList(booksFilteredByAuthor.get(author)));
        });

        return titlesFilteredByAuthor;
    }

    @Override
    public Set<String> getBooksPublishedThisYear() {
        LocalDate dateObj = LocalDate.now();
        List<BookEntity> books = getAllBooks();
        List<BookEntity> recentBooks = books.stream()
                .filter(book -> book.getPublishedDate().getYear() == dateObj.getYear())
                .collect(Collectors.toList());
        Set<String> booksPublishedThisYear = getNamesFromList(recentBooks);

        return booksPublishedThisYear;
    }

    @Override
    public Map<String, List<String>> getBooksByCategory() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        Map<String, List<CategoryEntity>> booksFilteredByCategory = categories.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getName));

        Map<String, List<String>> titlesFilteredByCategory = new HashMap<>();
        booksFilteredByCategory.forEach((String name, List<CategoryEntity> categoriesList) -> {
            List<String> bookNames = new ArrayList<>();
            categoriesList.forEach((CategoryEntity category) -> bookNames.addAll(getNamesFromSet(category.getBooks())));
            titlesFilteredByCategory.put(name, bookNames);
        });

        return titlesFilteredByCategory;
    }

    public Set<String> getNamesFromList(List<BookEntity> books) {
        Set<String> names = new HashSet<>();
        books.forEach(book -> names.add(book.getName()));

        return names;
    }

    public List<String> getNamesFromSet(Set<BookEntity> books) {
        List<String> names = new ArrayList<>();
        books.forEach(book -> names.add(book.getName()));

        return names;
    }

    public OldBookDto createOldBookDto(BookEntity book) {
        OldBookDto oldBook = new OldBookDto();
        oldBook.setName(book.getName());
        oldBook.setDescription(book.getDescription());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPublishedDate(book.getPublishedDate());
        oldBook.setCategories(book.getCategories());

        return oldBook;
    }


    @Override
    public OldBookDto getOldestBook() {
        List<BookEntity> books = getAllBooks();
        List<BookEntity> sortedByPublishedDateBooks = books.stream()
                .sorted(Comparator.comparing(BookEntity::getPublishedDate))
                .collect(Collectors.toList());
        BookEntity oldestBook = sortedByPublishedDateBooks.get(0);
        OldBookDto book = createOldBookDto(oldestBook);

        return book;
    }
}
