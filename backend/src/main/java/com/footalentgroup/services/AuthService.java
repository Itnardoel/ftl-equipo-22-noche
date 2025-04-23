package com.footalentgroup.services;

import com.footalentgroup.exception.EmailAlreadyExistsException;
import com.footalentgroup.models.dtos.NewUserDto;
import com.footalentgroup.models.entities.User;
import com.footalentgroup.repositories.IUserRepository;
import com.footalentgroup.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
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
    private final JwtUtil jwtUtil;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Autentica a un usuario con las credenciales proporcionadas (correo electrónico y contraseña).
     * Si las credenciales son válidas, genera un token JWT.
     */
    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User: " + email));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtil.generateToken(authentication, user);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Verifica que el correo electrónico proporcionado no esté ya en uso.
     */
    public void registerUser(NewUserDto newUserDto){
        if (this.userRepository.existsByEmail(newUserDto.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya existe");
        }

        // La contraseña es codificada
        User user = new User(newUserDto.getEmail(), newUserDto.getName(), newUserDto.getLastName(), passwordEncoder.encode(newUserDto.getPassword()) , newUserDto.getRole());
        this.userRepository.save(user); // Guarda el nuevo usuario en la base de datos.
    }
}
