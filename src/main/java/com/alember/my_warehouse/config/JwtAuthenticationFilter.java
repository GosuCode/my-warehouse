package com.alember.my_warehouse.config;

import com.alember.my_warehouse.services.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a Spring Security filter that intercepts incoming HTTP requests
 * to extract the JWT token from the Authorization header and authenticate the user.
 * If the JWT is valid, the filter sets the authentication in the SecurityContextHolder.
 * It extends OncePerRequestFilter to ensure the filter is executed once per request.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs a JwtAuthenticationFilter with the provided JwtService, UserDetailsService,
     * and HandlerExceptionResolver.
     *
     * @param jwtService The JwtService used for token extraction and validation.
     * @param userDetailsService The UserDetailsService used to load user details.
     * @param handlerExceptionResolver The exception resolver for handling any exceptions that occur during filtering.
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Filters incoming HTTP requests to extract and validate the JWT token. If the token is valid,
     * it sets the authentication in the SecurityContextHolder.
     *
     * @param request The HTTP request being processed.
     * @param response The HTTP response to be sent.
     * @param filterChain The filter chain to pass the request and response to the next filter.
     * @throws ServletException If the filter encounters a servlet-related error.
     * @throws IOException If an input/output error occurs during the filter execution.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // If the Authorization header is missing or does not start with "Bearer ", pass the request to the next filter.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT from the Authorization header
            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);

            // Retrieve the current authentication object from the SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If the user email is not null and no authentication exists, authenticate the user
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validate the JWT token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create an authentication token and set it in the SecurityContextHolder
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set the details for the authentication token (such as the remote address)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Pass the request to the next filter in the chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // If an exception occurs, handle it using the handlerExceptionResolver
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
