package com.footalentgroup.controllers;

import com.footalentgroup.models.dtos.LoginUserDto;
import com.footalentgroup.models.dtos.NewUserDto;
import com.footalentgroup.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping(AuthController.AUTH)
@RequiredArgsConstructor
public class AuthController {
    public static final String AUTH = "/auth";
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    private final AuthService authService;


    /**
     * login maneja el inicio de sesión.
     * Valida las credenciales del usuario (email y contraseña) y retorna un token JWT si son correctas.
     * Si las credenciales son incorrectas, devuelve un error con un mensaje
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authService.authenticate(loginUserDto.getEmail(), loginUserDto.getPassword()));
    }

    /**
     * Maneja el registro de un nuevo usuario.
     * Valida los datos de entrada y registra al usuario en el sistema si el email no está en uso.
     * Si el email ya existe, se lanza una excepción con un mensaje descriptivo.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto) {
        authService.registerUser(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
    }

    /**
     * Verifica si el usuario está autenticado
     */
    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(){

        return ResponseEntity.ok().body("Autenticado");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication authentication) {
        logoutHandler.logout(request,response,authentication);

        return ResponseEntity.ok("Logout exitoso");
    }
}
