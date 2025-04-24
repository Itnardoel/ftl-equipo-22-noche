package com.footalentgroup.services;

import com.footalentgroup.models.dtos.UserDto;
import com.footalentgroup.models.entities.User;
import com.footalentgroup.models.enums.RoleList;
import com.footalentgroup.repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;

    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario: " + id));

        return new UserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean isAdmin(Authentication authentication) {
        boolean isAdmin = false;

        Optional<User> userOptional = userRepository.findByEmail(authentication.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            isAdmin = user.getRole() == RoleList.ROLE_ADMIN;
        }
        return isAdmin;
    }

}
