package com.training.booklist.config;

import com.training.booklist.dao.UserDao;
import com.training.booklist.entities.AuthorityEntity;
import com.training.booklist.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<UserEntity> user = userDao.findAllByUsername(username);
        if(user.size() == 0) {
            if(passwordEncoder.matches(password, user.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid Password");
            }
        } else {
            throw new BadCredentialsException("This user is not registered");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<AuthorityEntity> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(AuthorityEntity authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
