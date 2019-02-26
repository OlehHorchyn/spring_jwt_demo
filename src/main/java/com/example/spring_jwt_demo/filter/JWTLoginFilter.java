package com.example.spring_jwt_demo.filter;

import com.example.spring_jwt_demo.models.User;
import com.example.spring_jwt_demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

//    During the authentication attempt,
// which is dealt by the attemptAuthentication method,
// we retrieve the username and password from the request.
// After they are retrieved, we use the AuthenticationManager to verify that these details match with an existing user.
// If it does, we enter the successfulAuthentication method.
// In this method we fetch the name from the authenticated user,
// and pass it on to TokenAuthenticationService, which will then add a JWT to the response.

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        User creds = new ObjectMapper()
                .readValue(httpServletRequest.getInputStream(), User.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, // body params
            HttpServletResponse res, //resp for client
            FilterChain chain,
            Authentication auth) throws IOException, ServletException
    {
        String jwToken = Jwts.builder()
                .setSubject(auth.getName())
                .signWith(SignatureAlgorithm.HS512, "yes".getBytes())
//                .setExpiration(new Date(System.currentTimeMillis() + 15000))
                .compact();

        System.out.println(jwToken);

        res.addHeader("Authorization","Bearer " + jwToken);

    }
}