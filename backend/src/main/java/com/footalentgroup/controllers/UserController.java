package com.footalentgroup.controllers;

import com.footalentgroup.models.dtos.UserDto;
import com.footalentgroup.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getUserByID/{id}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable Long id, Authentication authentication) {
     UserDto user= userService.getUserById(id);

     //si el usuario no es admin y no son sus  propios datos
     if(!userService.isAdmin(authentication) && !user.getEmail().equals(authentication.getName())){
         return ResponseEntity.status(403).build();
     }
     return ResponseEntity.ok(user);
    }
}
