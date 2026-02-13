package com.restaurantes.restaurante_api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Trata exceções globalmente sem usar @RestControllerAdvice, para evitar
 * incompatibilidade com springdoc quando a IDE usa versão 2.3 no classpath.
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        if (ex instanceof ResourceNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write(objectMapper.writeValueAsString(
                        Map.of("mensagem", ex.getMessage())));
            } catch (Exception e) {
                // fallback silencioso
            }
            return new ModelAndView();
        }
        return null; // outros erros seguem o fluxo normal do Spring
    }
}
