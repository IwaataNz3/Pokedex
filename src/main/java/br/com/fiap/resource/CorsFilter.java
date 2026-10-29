package br.com.fiap.resource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// CORS (A44): libera o navegador para chamar esta API a partir de outra origem
// (ex.: o front em React rodando em http://localhost:5173).
@Component  // o Spring registra o filtro sozinho; ele roda antes de toda requisição
public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");                                // qualquer origem
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // verbos liberados
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");     // cabeçalhos liberados
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");                                  // navegador guarda por 1h
        // o navegador manda um OPTIONS antes de POST/PUT/DELETE para perguntar se pode;
        // respondemos 200 na hora, sem passar pelo Resource
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // segue a requisição normalmente até o Resource
        filterChain.doFilter(request, response);
    }
}
