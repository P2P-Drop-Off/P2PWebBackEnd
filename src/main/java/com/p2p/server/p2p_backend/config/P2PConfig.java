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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.List;


@Configuration
public class P2PConfig implements WebMvcConfigurer {

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
                .setStorageBucket("p2pdropoff.firebasestorage.app")
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


}
