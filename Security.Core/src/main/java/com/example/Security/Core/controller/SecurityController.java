package com.example.Security.Core.controller;

import com.example.Security.Core.dto.AuthenticationRequest;
import com.example.Security.Core.dto.AuthenticationResponse;
import com.example.Security.Core.entities.User;
import com.example.Security.Core.repository.UserRepository;
import com.example.Security.Core.services.Impl.UserServiceImpl;
import com.example.Security.Core.utility.JwtUtil;
import com.example.Security.Core.utility.MD5Util;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
    private final UserServiceImpl userService;

    @Autowired
    public SecurityController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Аутентифицировать пользователя", description = "Аутентифицирует пользователя")
    public  ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        // Найти пользователя по имени
        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse("Пользователь не найден"));
        }

        // Хеширование пароля для сравнения
        String hashedPassword = MD5Util.hashPassword(request.getPassword());

        // Проверка пароля
        if (!hashedPassword.equals(user.getPassword())) {
            return ResponseEntity.status(401).body(new AuthenticationResponse("Неправильный логин или пароль"));
        }

        // Генерация токена и отправка в ответ
        String token = JwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


}
