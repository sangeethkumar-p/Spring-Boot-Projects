package com.doc.swagger.SwaggerDocumentation.controller;

import com.doc.swagger.SwaggerDocumentation.dto.UserDto;
import com.doc.swagger.SwaggerDocumentation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto user){
        return  userService.verifyUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        try{
            UserDto savedUser = userService.createNewUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<UserDto> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){

        try{
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
