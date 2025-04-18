package com.footalentgroup.services;

import com.footalentgroup.exception.EmailAlreadyExistsException;
import com.footalentgroup.models.dtos.NewUserDto;
import com.footalentgroup.models.entities.User;
import com.footalentgroup.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación que gestiona la autenticación de usuarios, el registro de nuevos usuarios
 * y la generación de tokens JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Autentica a un usuario con las credenciales proporcionadas (correo electrónico y contraseña).
     * Si las credenciales son válidas, genera un token JWT.
     */
    public String authenticate(String email, String password) {
        // Crea un token de autenticación con las credenciales
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // Autentica al usuario
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        return jwtUtil.generateToken(authResult);  // Genera y retorna el token JWT.
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Verifica que el correo electrónico proporcionado no esté ya en uso.
     */
    public void registerUser(NewUserDto newUserDto){
        if (userService.existsByEmail(newUserDto.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya existe");
        }

        // La contraseña es codificada
        User user = new User(newUserDto.getEmail(), newUserDto.getName(), newUserDto.getLastName(), passwordEncoder.encode(newUserDto.getPassword()) , newUserDto.getRole());
        userService.save(user); // Guarda el nuevo usuario en la base de datos.
    }
}
