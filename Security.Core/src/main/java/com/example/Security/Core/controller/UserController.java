package com.example.Security.Core.controller;

//import io.swagger.v3.oas.annotations.Operation;
import com.example.Security.Core.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "Добавить пользователя", description = "Добавляет пользователя")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        userService.createUser(username, password);
        return ResponseEntity.ok("Пользователь успешно добавлен");
    }

    @PutMapping("/{userId}/block")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Заблокировать пользователя", description = "Заблокирует пользователя по ID")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok("Пользователь успешно заблокирован");
    }

    @PutMapping("/{useId}/unblock")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Разблокировать пользователя", description = "Разблокирует пользователя по ID")
    public ResponseEntity<String> unblock(@PathVariable Long userId) {
        userService.unblockUser(userId);
        return ResponseEntity.ok("Пользователь успешно раблокирован");
    }

    @DeleteMapping("/{userId}/delete")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по ID")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Пользователь успешно удален");
    }

    @PutMapping("/{userId}/undelete")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Восстановить пользователя", description = "Восстанавливает ранее удаленного пользователя")
    public ResponseEntity<String> undeleteUser(@PathVariable Long userId) {
        userService.undeleteUser(userId);
        return ResponseEntity.ok("Пользователь успешно восстановлен");
    }

    @PutMapping("/{userId}/changePassword")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Изменить пароль", description = "Обновляет пароль пользователя по ID")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestParam String newPassword) {
        userService.changePassword(userId, newPassword);
        return ResponseEntity.ok("Пароль успешно изменен");
    }

}
