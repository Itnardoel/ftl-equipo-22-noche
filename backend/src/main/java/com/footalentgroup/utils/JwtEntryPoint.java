package com.footalentgroup.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile("!test")
public class JwtEntryPoint implements AuthenticationEntryPoint {

    /**
     * Si un usuario intenta acceder a un recurso sin estar autenticado,
     * se devuelve una respuesta  (No autorizado).
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
    }
}
