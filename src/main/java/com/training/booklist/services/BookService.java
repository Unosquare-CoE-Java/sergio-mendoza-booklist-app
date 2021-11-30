package com.training.booklist.services;

import com.training.booklist.dto.BookDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService implements Books {
    private List<BookDto> books = new ArrayList();

    @Override
    public List<BookDto> getBooks() {


        BookDto witcher = new BookDto();

        witcher.setAuthor("Andrzej Sapkowski");
        witcher.setDescription("Geralt of Rivia is a witcher, a monster hunter");
        witcher.setIsbn("45882");
        witcher.setPublisher("Planeta");
        witcher.setPublishedDate("1986");
        witcher.setName("The Last Wish");

        books.add(witcher);

        return books;
    }

    @Override
    public void saveBook(BookDto book) {
        books.add(book);
    }

    @Override
    public void updateBook(String name, BookDto book) {
        for(int i = 0; i <books.size(); i++) {
            BookDto b = books.get(i);
            if(b.getName().equals(name)) {
                books.set(i, book);
                return;
            }
        }
    }

    @Override
    public void deleteBook(String name) {
        books.removeIf(b -> b.getName().equals(name));
    }


}
