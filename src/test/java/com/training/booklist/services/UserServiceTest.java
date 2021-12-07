package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.UserDao;
import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    BookDao bookDao;

    @Test
    void saveUser() {
        UserDto user = UserDto.builder()
                .firstName("Luca")
                .lastName("Turilli")
                .country("Italy")
                .username("rhapsodyGuitarist")
                .password("ShredMachine1")
                .build();
        doNothing().when(userService).saveUser(user);
        userService.saveUser(user);

        verify(userService,times(1)).saveUser(user);
    }

    @Test
    void deleteNonexistentUser() {
        Long mockId = 5000L;

        doThrow(BadRequestException.class).when(userService).deleteUser(anyLong());
        Assertions.assertThrows(BadRequestException.class, () -> userService.deleteUser(mockId));
    }

    @Test
    void updateNonexistentUser() {
        Long mockId = 5000L;
        UserEntity user = new UserEntity();

        doThrow(BadRequestException.class).when(userService).updateUser(mockId, user);
        Assertions.assertThrows(BadRequestException.class, () -> userService.updateUser(mockId, user));
    }

    @Test
    void addBook() {
        UserEntity user = new UserEntity();
        user.setFirstName("Luca");
        user.setLastName("Turilli");
        user.setCountry("Italy");
        user.setUsername("rhapsodyGuitarist");
        user.setPassword("ShredMachine1");
        userDao.save(user);

        BookEntity book = new BookEntity();
        book.setAuthor("Manuel Lopez Michelone");
        book.setDescription("Analisis de los recursos informáticos que se usan para analizar la vida artificial");
        book.setIsbn("655435469494");
        book.setName("Jugando a ser dios, experimentos en vida artificial");
        book.setPublisher("UNAM");
        book.setPublishedDate("18/08/2020");
        bookDao.save(book);

        Long userId = user.getId();
        Long bookId = book.getId();

        doNothing().when(userService).addBook(bookId,userId);
        userService.addBook(bookId,userId);

        verify(userService,times(1)).addBook(bookId,userId);
    }

}