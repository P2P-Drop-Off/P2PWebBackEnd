package com.p2p.server.p2p_backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class SpringSecurity {

    private final FirebaseTokenFilter firebaseTokenFilter;

    public SpringSecurity(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("filterChain");
        http
                .cors(cors -> {}) 
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/items/*/approve").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/items/**").authenticated()

                    // public listing link:
                    .requestMatchers(HttpMethod.GET, "/api/items/*").permitAll()

                    .requestMatchers("/auth").permitAll()
                    .anyRequest().authenticated()
                ).addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
