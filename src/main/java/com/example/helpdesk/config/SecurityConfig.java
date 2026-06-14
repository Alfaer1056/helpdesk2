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
                        // Статические ресурсы
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // H2 Console
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        // ВАЖНО: страница логина должна быть доступна всем
                        .requestMatchers("/login").permitAll()

                        // Публичные GET страницы
                        .requestMatchers(HttpMethod.GET,
                                "/", "/about", "/contacts",
                                "/tickets/new", "/tickets/*/success"
                        ).permitAll()
                        // Публичный POST
                        .requestMatchers(HttpMethod.POST, "/tickets").permitAll()

                        // Админ-зона
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // ВСЕ остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")  // Явно указываем URL обработки
                        .defaultSuccessUrl("/admin/tickets", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("USER", "ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}