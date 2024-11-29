package com.example.Security.Core.services.Impl;

import com.example.Security.Core.entities.User;
import com.example.Security.Core.repository.UserRepository;
import com.example.Security.Core.services.UserService;
import com.example.Security.Core.utility.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.hashPassword(password));
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setBlockedDate(null); // Пока не заблокирован
        user.setDeletedDate(null); // Пока не удален
        user.setPasswordExpired(null);
        user.setRoles(Set.of("USER"));
        userRepository.save(user);
    }

    @Override
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsBlocked(true);
        user.setBlockedDate(new Date());
        userRepository.save(user);
    }

    @Override
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsBlocked(false);
        user.setBlockedDate(null);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(true);
        user.setDeletedDate(new Date());
        userRepository.save(user);
    }

    @Override
    public void undeleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(false);
        user.setDeletedDate(null);
        userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(MD5Util.hashPassword(newPassword));
        user.setPasswordExpired(new Date());
        userRepository.save(user);
    }

}
