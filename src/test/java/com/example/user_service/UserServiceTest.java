package com.example.user_service;

import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Test
    void createUser_ShouldCreateUser_WhenValidInput() {
        // Datos de prueba
        UserEntradaDTO userDTO = new UserEntradaDTO("John Doe","password123" , "john@example.com");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        User savedUser = new User();
        savedUser.setName(userDTO.getName());
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setRole(adminRole);
        savedUser.setPassword("hashedPassword");

        UserSalidaDTO expectedResponse = new UserSalidaDTO("John Doe", "john@example.com", 1L, "ADMIN");

        // Mockeamos las dependencias
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty()); // No existe usuario
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(adminRole)); // Existe el rol ADMIN
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword"); // Hashear la contraseña
        when(userMapper.toEntity(userDTO)).thenReturn(savedUser);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.EntitytoSalidaDTO(savedUser)).thenReturn(expectedResponse);

        // Ejecutar el metodo
        UserSalidaDTO result = userService.createUser(userDTO);

        // Verificar que el resultado es el esperado
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(adminRole, result.getRoleName());

        // Verificar que los métodos mockeados fueron llamados correctamente
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(roleRepository).findByName("ADMIN");
        verify(passwordEncoder).encode(userDTO.getPassword());
        verify(userRepository).save(any(User.class));
        verify(userMapper).EntitytoSalidaDTO(savedUser);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Datos de prueba
        UserEntradaDTO userDTO = new UserEntradaDTO("John Doe", "password123", "john@example.com");
        User existingUser = new User();
        existingUser.setEmail(userDTO.getEmail());

        // Simular que el usuario ya existe
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        // Ejecutar y verificar que lanza la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));

        assertEquals("El usuario con este email ya existe.", exception.getMessage());

        // Verificar que no se intentó buscar el rol ni guardar el usuario
        verify(roleRepository, never()).findByName(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenAdminRoleNotFound() {
        // Datos de prueba
        UserEntradaDTO userDTO = new UserEntradaDTO("John Doe", "password123", "john@example.com");

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        // Mockeamos la búsqueda del usuario (no existe)
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.empty()); // No se encuentra el rol ADMIN

        // Ejecutar y verificar que lanza la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));

        assertEquals("El rol ADMIN no existe en la base de datos", exception.getMessage());

        // Verificar que no se intentó guardar el usuario
        verify(userRepository, never()).save(any(User.class));
    }
}

