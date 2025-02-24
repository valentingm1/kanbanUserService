package com.example.user_service.controller;

import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;
import com.example.user_service.service.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private iUserService UserService;

    @Autowired
    public UserController(iUserService userService) {
        UserService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserSalidaDTO> createUser(@Validated @RequestBody UserEntradaDTO userEntradaDTO) {
        return new ResponseEntity<>(UserService.createUser(userEntradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserSalidaDTO>> getAllUsers() {
        return new ResponseEntity<>(UserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserSalidaDTO> getOneUser(@PathVariable Long id){
        try {
            return new ResponseEntity<>(UserService.getOneUser(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            UserService.deleteOneUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found con mensaje
        }
    }

}
