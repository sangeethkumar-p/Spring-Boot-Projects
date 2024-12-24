package com.doc.swagger.SwaggerDocumentation.service;

import com.doc.swagger.SwaggerDocumentation.dto.UserDto;
import com.doc.swagger.SwaggerDocumentation.entity.User;
import com.doc.swagger.SwaggerDocumentation.mapper.UserMapper;
import com.doc.swagger.SwaggerDocumentation.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations.
 * This class provides methods for creating, verifying, retrieving, and deleting users.
 * It integrates with Spring Security for authentication and JWTService for token generation.
 */
@Service
public class UserService {

    /**
     * Repository for accessing user data in the database.
     */
    private final UserRepository userRepository;

    /**
     * Manager for handling user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Service for generating and validating JWTs.
     */
    private final JWTService jwtService;

    /**
     * Constructor to initialize the UserService with required dependencies.
     *
     * @param userRepository          Repository for user data access.
     * @param authenticationManager   Manager for user authentication.
     * @param jwtService              Service for JWT operations.
     */
    public UserService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userDto The user data transfer object containing user details.
     * @return The created user as a UserDto.
     * @throws RuntimeException if the email is already associated with another user.
     */
    public UserDto createNewUser(UserDto userDto) {
        // Check if the email is already registered
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email Id entered is already associated with another user. Please try again with a different email address.");
        }

        // Map UserDto to User entity
        User user = UserMapper.mapToUser(userDto);

        // Encrypt the password before saving
        String password = passwordEncoder().encode(user.getPassword());
        user.setPassword(password);

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Map the saved user back to UserDto and return
        return UserMapper.mapToUserDto(savedUser);
    }

    /**
     * Provides a BCryptPasswordEncoder instance for password encoding.
     *
     * @return A BCryptPasswordEncoder with strength 12.
     */
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Verifies a user's credentials and generates a JWT if authentication is successful.
     *
     * @param userDto The user data transfer object containing login credentials.
     * @return A JWT if authentication is successful, otherwise "Failure".
     */
    public String verifyUser(UserDto userDto) {
        // Map UserDto to User entity
        User user = UserMapper.mapToUser(userDto);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
        );

        // If authentication is successful, generate a JWT
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        }

        // Return "Failure" if authentication fails
        return "Failure";
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of UserDto objects representing all users.
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @throws UsernameNotFoundException if the user ID is not found in the database.
     */
    public void deleteUserById(Long id) {
        // Check if the user exists
        if (userRepository.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("User Id not found");
        }

        // Delete the user
        userRepository.deleteById(id);
    }
}