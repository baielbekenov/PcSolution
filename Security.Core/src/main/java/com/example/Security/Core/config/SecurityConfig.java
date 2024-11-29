package com.example.Security.Core.config;

import com.example.Security.Core.utility.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless для работы с токенами
                .formLogin().disable() // Отключаем стандартную форму логина
                .authorizeRequests(auth -> auth
                        .requestMatchers(  // Публичные маршруты
                                "/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/users/create", // Публичный маршрут для регистрации
                                "/authenticate", // Публичный маршрут для аутентификации
                                "/h2-console/**" // Публичный маршрут для доступа к H2-консоли
                        ).permitAll()  // Разрешить доступ без токена
                        .requestMatchers("/users/**") // Ваши защищенные эндпоинты
                        .authenticated()  // Требуем аутентификацию для всех остальных
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Добавляем фильтр для обработки JWT
                .headers().frameOptions().sameOrigin(); // Разрешение для iframe в H2-консоли

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}
