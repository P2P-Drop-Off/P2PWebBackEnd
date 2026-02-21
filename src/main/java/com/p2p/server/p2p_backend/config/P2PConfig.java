package com.p2p.server.p2p_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Configuration
public class P2PConfig {

    private final Map<String, Object> secrets;

    public P2PConfig(@Value("classpath:secret.json") Resource privateKey) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.secrets = mapper.readValue(privateKey.getInputStream(), Map.class);
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Object firebaseNode = secrets.get("firebase");
        byte[] firebaseBytes = mapper.writeValueAsBytes(firebaseNode);

        InputStream credentials =
                new ByteArrayInputStream(firebaseBytes);

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.getApps().isEmpty()
                ? FirebaseApp.initializeApp(firebaseOptions)
                : FirebaseApp.getInstance();

        return firebaseApp;
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) throws IOException {
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    // Spring Security UserDetailsService Bean
    @Bean 
    public UserDetailsService userDetailsService() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> springNode = (Map<String, Object>) secrets.get("spring");
        Map<String, Object> securityNode = (Map<String, Object>) springNode.get("security");
        Map<String, String> userNode = (Map<String, String>) securityNode.get("user");

        String username = userNode.get("name");
        String password = userNode.get("password");

        return new InMemoryUserDetailsManager(
                User.withUsername(username)
                        .password("{noop}" + password)
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }



}
