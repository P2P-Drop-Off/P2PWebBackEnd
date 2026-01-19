package com.p2p.server.P2PWebBackEnd.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

@Configuration
public class P2PConfig {
    @Value("classpath:secret.json")
    private Resource privateKey;

    @Bean
    public Firestore firestore() throws IOException {

        InputStream credentials =
                new ByteArrayInputStream(privateKey.getContentAsByteArray());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.getApps().isEmpty()
                ? FirebaseApp.initializeApp(firebaseOptions)
                : FirebaseApp.getInstance();

        return FirestoreClient.getFirestore(firebaseApp);
    }
}
