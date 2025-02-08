package com.example.munchly.filters;

import com.example.munchly.entities.User;
import com.example.munchly.services.JwtService;
import com.example.munchly.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try{
            final String requestTokenHeader = request.getHeader("Authorization");

            String requestURI = request.getRequestURI();

            if (requestURI.startsWith("/auth") || requestURI.startsWith("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")){
                System.out.println("No Bearer token found");
                filterChain.doFilter(request,response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdFromToken(token);
            if(userId == null){
                System.out.println("Invalid token: userId is null");
                filterChain.doFilter(request,response);
                return;
            }
            System.out.println("Token is valid. User ID: " + userId);

            User user = userService.getUserById(userId);
            if(user == null){
                System.out.println("User not found with ID + " + userId);
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }catch (Exception ex){
            System.out.println("Error during JWT authentication: " + ex.getMessage());
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
