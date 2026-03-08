package com.api.twitter.security.configuration;

import com.api.twitter.security.domain.service.TokenService;
import com.api.twitter.security.domain.service.UserCredentialsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserCredentialsService userCredentialsService;

    public SecurityAuthenticationFilter(TokenService tokenService, UserCredentialsService userCredentialsService) {
        this.tokenService = tokenService;
        this.userCredentialsService = userCredentialsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        if (token != null){
            try {
                String username = tokenService.validateTokenAndGetUsername(token);
                UserDetails userDetails = userCredentialsService.loadUserByUsername(username);
                var userPassToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userPassToken);
            } catch (Exception e){
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        String tokenWithoutPrefix = authHeader.replace("Bearer ", "");
        return tokenWithoutPrefix;
    }
}
