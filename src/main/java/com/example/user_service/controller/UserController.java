package com.example.user_service.controller;


import com.example.user_service.dto.entrada.SignInEntradaDTO;
import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.SignInSalidaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;
import com.example.user_service.service.iUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "All /users/ related endpoints")
public class UserController {
    private final iUserService UserService;

    @Autowired
    public UserController(iUserService userService) {
        this.UserService = userService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created entity.")
    public ResponseEntity<UserSalidaDTO> createUser(@Validated @RequestBody UserEntradaDTO userEntradaDTO) {
        return new ResponseEntity<>(UserService.createUser(userEntradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    public ResponseEntity<List<UserSalidaDTO>> getAllUsers() {
        return new ResponseEntity<>(UserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieves a user given its ID.")
    public ResponseEntity<UserSalidaDTO> getOneUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(UserService.getOneUser(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user given its ID.")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            UserService.deleteOneUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    @Operation(summary = "User login", description = "Authenticates a user and returns a token if successful.")
    public ResponseEntity<?> login(@RequestBody SignInEntradaDTO authRequest) {
        try {
            SignInSalidaDTO response = UserService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}