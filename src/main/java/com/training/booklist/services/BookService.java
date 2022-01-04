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
        Optional<BookEntity> minBook = books.stream()
                .min(Comparator.comparing(BookEntity::getPublishedDate));
        BookEntity oldestBook = minBook.get();
        OldBookDto book = createOldBookDto(oldestBook);

        return book;
    }

    public String replaceSpacesWithPlus(String s) {
        s = s.replaceAll("\\s", "+");
        return s;
    }

    //This call is necessary to get the Isbn used inside Open Library for the desired book
    //@Override
    public BookSearchApiResultDto callToSearchByTitleApi(String book) {
            String url = "http://openlibrary.org/search.json?title=" + replaceSpacesWithPlus(book);
            RestTemplate rest = new RestTemplate();
            BookSearchApiResultDto result = rest.getForObject(url, BookSearchApiResultDto.class);

            return result;
    }

   // @Override
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
