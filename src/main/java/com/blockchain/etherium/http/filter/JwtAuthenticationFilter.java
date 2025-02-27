package com.blockchain.etherium.http.filter;

import com.blockchain.etherium.domain.service.UserHistoryService;
import com.blockchain.etherium.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private final JwtUtil jwtUtil;
    private final UserHistoryService userHistoryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(AUTH_TOKEN);
        if (token != null && jwtUtil.validateToken(token)) {
            Authentication authentication = jwtUtil.getAuthentication(token);

            String username = (String) authentication.getPrincipal();
            String[] transactionHashes = request.getParameterValues("transactionHashes");

            if (transactionHashes != null && transactionHashes.length > 0) {
                userHistoryService.storeUserSearchHistory(username, transactionHashes);
            }

            request.setAttribute("username", username);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
