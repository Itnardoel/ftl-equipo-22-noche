package com.footalentgroup.controllers;

import com.footalentgroup.exception.ForbiddenException;
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
@RequestMapping(UserController.USERS)
@RequiredArgsConstructor
public class UserController {
    public static final String USERS = "/users";

    private final UserService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getUserByID/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDto> getUserByID(@PathVariable Long id, Authentication authentication) {
        UserDto user = userService.getUserById(id);

        // Si el usuario no es admin y no son sus  propios datos
        if (!userService.isAdmin(authentication) && !user.getEmail().equals(authentication.getName())){
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(user);
    }
}
