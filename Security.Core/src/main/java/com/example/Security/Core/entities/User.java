package com.example.Security.Core.entities;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "users", schema = "security")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    public String username;

    @Column(nullable = false, length = 255)
    public String password;

    @Column(name = "is_blocked")
    public boolean isBlocked;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blocked_date")
    public Date blockedDate;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    public Date deletedDate;

    @Column(name = "description", length = 255)
    public String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "password_expired")
    public Date passwordExpired;

    @ElementCollection(fetch = FetchType.EAGER)  // Хранение списка ролей
    private Set<String> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Date getBlockedDate() {
        return blockedDate;
    }

    public void setBlockedDate(Date blockedDate) {
        this.blockedDate = blockedDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Date passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // Добавляем префикс "ROLE_" для соответствия стандарту Spring Security
                .collect(Collectors.toList());
    }

}
