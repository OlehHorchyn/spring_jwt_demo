package com.example.spring_jwt_demo.filter;

import com.example.spring_jwt_demo.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTAuthenticationFilter  extends GenericFilterBean {

    
    @Autowired
    private UserService userService;



    //ServletRequest servletRequest, ServletResponse servletResponse - це запит і відповідь сервера
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("RequestProcessingJWTFilter");

        Authentication authentication = null;

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String token = httpServletRequest.getHeader("Authorization");

        if(token != null){
            String user = Jwts.parser()
                    .setSigningKey("yes".getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            System.out.println(user + "!!!!!!!!!!!!!-!!!!!!!!!!!!!");

            System.out.println(userService.loadUserByUsername(user));

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        }

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
