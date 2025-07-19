package com.scaler.resumescreener.services;

import com.scaler.resumescreener.models.User;
import com.scaler.resumescreener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        builder.roles(user.getRole().name()); // assuming `Role` is an enum like ADMIN, USER, etc.

        return builder.build();
    }
}
