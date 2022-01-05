package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.OldBookDto;
import com.training.booklist.dto.BookDocsDto;
import com.training.booklist.dto.BookSearchApiResultDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService implements Books {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestTemplate restTemplate;

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
        book.setCoverUrl(getBookCover(book.getName()));
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
    public Map<String, Map<String, String>> getBooksByAuthor() {
        List<BookEntity> books = getAllBooks();
        Map<String, List<BookEntity>> booksFilteredByAuthor = books.stream()
                .collect(Collectors.groupingBy(BookEntity::getAuthor));
    //  Getting the titles
        Map<String, Map<String, String>> titlesFilteredByAuthor = new HashMap<>();
        booksFilteredByAuthor.forEach((String author, List<BookEntity> bookList) -> {
            titlesFilteredByAuthor.put(author, getNamesAndCoversFromList(booksFilteredByAuthor.get(author)));
        });

        return titlesFilteredByAuthor;
    }

    @Override
    public Map<String, String> getBooksPublishedThisYear() {
        LocalDate dateObj = LocalDate.now();
        List<BookEntity> books = getAllBooks();
        List<BookEntity> recentBooks = books.stream()
                .filter(book -> book.getPublishedDate().getYear() == dateObj.getYear())
                .collect(Collectors.toList());
        Map<String, String> booksPublishedThisYear = getNamesAndCoversFromList(recentBooks);

        return booksPublishedThisYear;
    }

    @Override
    public Map<String, List<Map<String, String>>> getBooksByCategory() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        Map<String, List<CategoryEntity>> booksFilteredByCategory = categories.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getName));

        Map<String, List<Map<String, String>>> titlesFilteredByCategory = new HashMap<>();
        booksFilteredByCategory.forEach((String name, List<CategoryEntity> categoriesList) -> {
            List<Map<String, String>> bookNames = new ArrayList<>();
            categoriesList.forEach((CategoryEntity category) -> {
                // We don't want empty fields in the result
                if(!category.getBooks().isEmpty()) {
                    bookNames.add(getNamesAndCoversFromSet(category.getBooks()));
                    titlesFilteredByCategory.put(name, bookNames);
                }
            });
        });

        return titlesFilteredByCategory;
    }

    @Override
    public Map<String, String> getNamesAndCoversFromList(List<BookEntity> books) {
        Map<String, String> names = new HashMap<>();
        books.forEach(book -> names.put(book.getName(), book.getCoverUrl()));

        return names;
    }

    @Override
    public Map<String, String> getNamesAndCoversFromSet(Set<BookEntity> books) {
        Map<String, String> namesAndCovers = new HashMap<>();
        books.forEach(book -> namesAndCovers.put(book.getName(), book.getCoverUrl()));

        return namesAndCovers;
    }

    @Override
    public OldBookDto createOldBookDto(BookEntity book) {
        OldBookDto oldBook = new OldBookDto();
        oldBook.setName(book.getName());
        oldBook.setDescription(book.getDescription());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPublishedDate(book.getPublishedDate());
        oldBook.setCategories(book.getCategories());
        oldBook.setCoverUrl(book.getCoverUrl());

        return oldBook;
    }


    @Override
    public OldBookDto getOldestBook() {
        List<BookEntity> books = getAllBooks();
        Optional<BookEntity> minBook = books.stream()
                .min(Comparator.comparing(BookEntity::getPublishedDate));
        BookEntity oldestBook = minBook.get();
        OldBookDto book = createOldBookDto(oldestBook);

        return book;
    }

    @Override
    public String replaceSpacesWithPlus(String s) {
        s = s.replaceAll("\\s", "+");
        return s;
    }

    //This call is necessary to get the Isbn used inside Open Library for the desired book
    @Override
    public BookSearchApiResultDto callToSearchByTitleApi(String book) {
            String url = "http://openlibrary.org/search.json?title=" + replaceSpacesWithPlus(book);
            BookSearchApiResultDto result = restTemplate.getForObject(url, BookSearchApiResultDto.class);

            return result;
    }

    @Override
    public String getBookCover(String book) {
        try {
            List<BookDocsDto> docs = callToSearchByTitleApi(book).getDocs();
            BookDocsDto firstResult = docs.get(0);
            String firstIsbn = firstResult.getIsbn().get(0);
            String bookCoverURL = "https://covers.openlibrary.org/b/isbn/" + firstIsbn + "-L.jpg";

            return bookCoverURL;
        } catch(IndexOutOfBoundsException i) {
            return "No covers found for the requested book";
        }
    }

    // To update books already stored in db
    @Override
    public void updateBooksWithCoverUrl() {
        List<BookEntity> bookEntity = new ArrayList<>();
        bookDao.findAll()
                .forEach((BookEntity book) -> {
                    if(book.getCoverUrl() == null) {
                        book.setCoverUrl(getBookCover(book.getName()));
                        bookDao.save(book);
                    }
                });
    }
}
