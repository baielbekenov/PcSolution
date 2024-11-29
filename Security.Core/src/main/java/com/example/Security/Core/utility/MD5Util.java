package com.example.Security.Core.utility;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());

            // Преобразуем байты в строку с использованием шестнадцатеричного представления
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b)); // Хэш в виде строки
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
