package com.maids.config;

import com.maids.exception.TokenException;
import com.maids.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // to create a constractor for any private final field!
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserEmail(jwt);
        System.out.println("the user email is: " + userEmail);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println(userDetails.getUsername() + " "+ userDetails.getAuthorities() + " "+ userDetails.getPassword() + " ");
            System.out.println("token is valid: " + jwtService.isTokenValid(jwt, userDetails));
            if(jwtService.isTokenValid(jwt, userDetails)){
                //needed by security context holder as parameter:
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                System.out.println(authToken.getPrincipal() +" "+ userDetails.getUsername() +" "+ userDetails.getPassword() );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("details: " + authToken.getDetails().toString());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("done!");
            }else {
                System.out.println("invalid toke key");
                throw new TokenException("Invalide Token Key");
            }
//            System.out.println("token not valid!");
        }
//        System.out.println("auth or no email provided!");
        filterChain.doFilter(request, response);
    }
}
