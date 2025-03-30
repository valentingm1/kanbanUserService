package com.example.user_service.service.impl;

import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.SignInSalidaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtUtil;
import com.example.user_service.service.iUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements iUserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService; //borrar despues de terminar las pruebas
    private final JwtUtil jwtUtil;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleService roleService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserSalidaDTO createUser(UserEntradaDTO userDTO){
        log.info("Creating user {}", userDTO.getName());

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        // No se me ocurre otra forma de asignar el rol
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role does not exist on db"));


        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hashear la contraseña
        user.setRole(adminRole); // Asignar el rol de ADMIN

        User savedUser = userRepository.save(user);

        
        UserSalidaDTO salidaDTO = userMapper.EntitytoSalidaDTO(savedUser);


        return salidaDTO;
    }

    @Override
    public List<UserSalidaDTO> getAllUsers(){
        log.info("Getting all users...");
        List<User> list = userRepository.findAll();
        return list.stream().map(userMapper::EntitytoSalidaDTO).collect(Collectors.toList());
    }

    @Override
    public UserSalidaDTO getOneUser(Long id){
        log.info("Searching for user with id {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));

        return userMapper.EntitytoSalidaDTO(user);
    }

    @Override
    public void deleteOneUser(Long id){
        log.info("Attempting to delete user with id {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + "not found"));

        userRepository.delete(user);
    }

    @Override
    public SignInSalidaDTO  authenticateUser(String email, String password) {
        // Obtener el usuario por email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        // Verificar que la contraseña sea correcta
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        // Generar el token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getName());


        // Devolver el token, rol, correo electrónico y nombre
        return new SignInSalidaDTO(token, user.getRole().getName(), user.getEmail(), user.getName());
    }



}
