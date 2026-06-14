package com.example.helpdesk.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем статические ресурсы
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // Разрешаем H2 Console для учебных целей
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        // Публичные GET страницы
                        .requestMatchers(HttpMethod.GET,
                                "/", "/about", "/contacts", "/login",
                                "/tickets/new", "/tickets/*/success"
                        ).permitAll()
                        // Публичный POST для создания заявки
                        .requestMatchers(HttpMethod.POST, "/tickets").permitAll()

                        // Админ-зона требует роль ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                // Отключаем CSRF для H2 Console
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )
                // Разрешаем фреймы для H2 Console
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                // Настройка формы логина
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/tickets", true)
                        .permitAll()
                )
                // Настройка выхода
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        // Добавляем страницу "Нет доступа" (опционально)
        http.exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Администратор
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("USER", "ADMIN")
                .build();

        // Обычный пользователь
        UserDetails user = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}