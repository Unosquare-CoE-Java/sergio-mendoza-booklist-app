package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.UserDao;
import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserService implements Users {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;


    // for testing purposes
    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> userEntity = new ArrayList<>();
        userDao.findAll()
                .forEach(userEntity::add);
        return userEntity;
    }

    private Date getCurrentDate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = dateObj.format(formatter);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter2.parse(formattedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public void saveUser(UserDto user) {
        UserEntity userEntity = new UserEntity();
        Date date;
        date = getCurrentDate();

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setCountry(user.getCountry());
        userEntity.setRegistrationDate(date);
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());

        userDao.save(userEntity);
    }

    @Override
    public void updateUser(Long id, UserDto user) {
        UserEntity userEntity = new UserEntity();

        if(userDao.existsById(id)) {
            userEntity = userDao.getById(id);
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setCountry(user.getCountry());
            userEntity.setUsername(user.getUsername());
            userEntity.setPassword(user.getPassword());

            userDao.save(userEntity);
        } else {
            throw new BadRequestException("User didn't exists ot there is a mistype error");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if(userDao.existsById(id)) {
            userDao.deleteById(id);
        } else {
            throw new BadRequestException("User didn't exists ot there is a mistype error");
        }
    }

    @Override
    public void addBook(Long bookId, Long userid) {
        if(userDao.existsById(userid) && bookDao.existsById(bookId)) {
            BookEntity book = bookDao.getBookEntityById(bookId);
            UserEntity user = userDao.getById(userid);
            user.addBook(book);
            userDao.save(user);
        }
    }
}
