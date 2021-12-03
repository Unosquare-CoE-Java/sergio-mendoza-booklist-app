package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dao.UserDao;
import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private BookDao bookDao;
    @Mock
    private UserEntity userEntity;
    @Mock
    private BookEntity bookEntity;
    @Mock
    private CategoryEntity categoryEntity;
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> postArgumentCaptor;

    @Test
    @DisplayName("Save a user")
    // id is a hardcoded value for now
    void saveUser() {

        UserDto user = new UserDto();
        user.setFirstName("Giannis");
        user.setLastName("Antetokoumpo");
        user.setPassword("1234");
        user.setCountry("Greece");
        user.setUsername("TheGreekFreak");

        userService.saveUser(user);

        Mockito.verify(userDao, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getFirstName()).isEqualTo("Giannis");

        /*
        Mockito.when(userDao.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.doThrow(userService.updateUser(2L, user));

        UserDto user = new UserDto();
        user.setFirstName("Pancho");
        user.setLastName("Pistolas");
        UserService userService = new UserService();
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(10L,user);
        });
        assertTrue(exception.getMessage().contains("User didn't exists ot there is a mistype error"));

         */
    }
}