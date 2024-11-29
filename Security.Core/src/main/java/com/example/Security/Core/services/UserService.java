package com.example.Security.Core.services;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(String username, String password);

    void blockUser(Long userId);

    void unblockUser(Long userId);

    void deleteUser(Long userId);

    void undeleteUser(Long userId);

    void changePassword(Long userId, String newPassword);
}
