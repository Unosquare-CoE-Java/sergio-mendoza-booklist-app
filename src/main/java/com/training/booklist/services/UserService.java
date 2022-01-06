package com.training.booklist.services;

import com.training.booklist.config.SecurityUser;
import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.TokenDao;
import com.training.booklist.dao.UserDao;
import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.AuthorityEntity;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.TokenEntity;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserService implements Users, UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired @Lazy
    private PasswordEncoder passwordEncoder;


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
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(userEntity);
    }

    @Override
    public void updateUser(Long id, UserEntity user) {
        if(userDao.existsById(id)) {
            user.setId(id);
            userDao.save(user);
        } else {
            throw new BadRequestException("User id: " + id + " didn't exists ot there is a mistype error");
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
    public void addToken(String username, String jwtToken) {
        UserEntity user = userDao.getByUsername(username);
        TokenEntity token = new TokenEntity();
        boolean alreadyInDB = tokenDao.existsByUser(user);
        if(!alreadyInDB) {
            token.setToken(jwtToken);
            token.setUser(user);

            tokenDao.save(token);
        } else {
            token = tokenDao.getByUser(user);
            token.setToken(jwtToken);

            tokenDao.save(token);
        }
    }

    @Override
    public void addAuthority(Long id, AuthorityEntity authority) {
        if(userDao.existsById(id)) {
            UserEntity user = userDao.getById(id);
            user.addAuthority(authority);
            userDao.save(user);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> user = userDao.findAllByUsername(username);
        if(user.size() == 0) {
            throw new UsernameNotFoundException("User details not found for user: " + username);
        }
        return new SecurityUser(user.get(0));
    }
}
