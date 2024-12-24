package com.doc.swagger.SwaggerDocumentation.service;

import com.doc.swagger.SwaggerDocumentation.dto.UserDto;
import com.doc.swagger.SwaggerDocumentation.entity.User;
import com.doc.swagger.SwaggerDocumentation.mapper.UserMapper;
import com.doc.swagger.SwaggerDocumentation.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User ID entered invalid");
        }

        return new CustomUserDetails(user);
    }
}
