package com.training.booklist.services;

import com.training.booklist.filter.JWTTokenValidatorFilter;
import com.training.booklist.filter.constants.SecurityConstants;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class AuthTest {
    @Test
    @SneakyThrows
    public void doFilterInternalWhenTokenIsValid() {
        JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
        String token = issueTokenForUser("Pancho");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/books");
        request.addHeader(SecurityConstants.JWT_HEADER, token);

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, mockFilterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication())
                .satisfies(authentication -> {
                    assertThat(authentication).isNotNull();
                    assertThat(authentication.getName()).isEqualTo("Pancho");
                });
    }

    private String issueTokenForUser(String username) {
        // At this moment this is a hardcoded token that will expired at 17/12/2021
        return "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTZXJnaW8gTWVuZG96YSIsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW1lIjoiUGFuY2hvIiwiYXV0aG9yaXRpZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNjM5NzY3NjA4LCJleHAiOjE2Mzk3NzEyMDh9.t2esmUGopc7jXedoyZSUvAsY0J67QYPhgWf9Rxr88nk";
    }

}
