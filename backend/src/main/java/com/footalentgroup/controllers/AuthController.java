package com.footalentgroup.controllers;

import com.footalentgroup.models.dtos.LoginUserDto;
import com.footalentgroup.models.dtos.NewUserDto;
import com.footalentgroup.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

  /**
 * login maneja el inicio de sesión.
 * Valida las credenciales del usuario (email y contraseña) y retorna un token JWT si son correctas.
 * Si las credenciales son incorrectas, devuelve un error con un mensaje
 */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise sus credenciales");
        }
        try {
            String jwt = authService.authenticate(loginUserDto.getEmail(), loginUserDto.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

/**
 * Maneja el registro de un nuevo usuario.
 * Valida los datos de entrada y registra al usuario en el sistema si el email no está en uso.
 * Si el email ya existe, se lanza una excepción con un mensaje descriptivo.
 */
 @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verifica si el usuario está autenticado
     */
    @GetMapping("/check-auth") //veriffica si usuario esta autenticado
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok().body("Autenticado");
    }
}
