package com.example.munchly.services;

import com.example.munchly.dto.SignUpDto;
import com.example.munchly.dto.UserDto;
import com.example.munchly.entities.User;
import com.example.munchly.entities.enums.Role;
import com.example.munchly.exception.ResourceNotFoundException;
import com.example.munchly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email " + username + " not available"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }


    public UserDto signUp(SignUpDto signUpDto){
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if(user.isPresent()){
            throw new BadCredentialsException("User with this email already exists " + signUpDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signUpDto, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        // Assign default role if not provided
        if (signUpDto.getRoles() == null || signUpDto.getRoles().isEmpty()) {
            toBeCreatedUser.setRoles(Set.of(Role.CUSTOMER)); // Default to CUSTOMER
        } else {
            toBeCreatedUser.setRoles(signUpDto.getRoles()); // Use provided roles
        }
        toBeCreatedUser.setActive(true);
        toBeCreatedUser.setCreatedAt(LocalDateTime.now());
        toBeCreatedUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDto.class);

    }

    public User save(User newUser){
        return userRepository.save(newUser);
    }

}
