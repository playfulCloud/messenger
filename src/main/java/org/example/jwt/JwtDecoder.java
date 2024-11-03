package org.example.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class JwtDecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static Map<String, Object> decodePayload(String token) throws IOException {
        String[] parts = token.split("\\.");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        return objectMapper.readValue(payload, Map.class);
    }

//    public static void main(String[] args) {
//        // Przykładowy token (zastąp go rzeczywistym tokenem)
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG5kb2UiLCJyb2xlIjoiYWRtaW4ifQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
//
//        try {
//            Map<String, Object> claims = decodePayload(token);
//            System.out.println("Payload claims: " + claims);
//
//            // Przykład: Dostęp do roszczenia 'username'
//            String username = (String) claims.get("username");
//            System.out.println("Username: " + username);
//
//            // Przykład: Dostęp do roszczenia 'role'
//            String role = (String) claims.get("role");
//            System.out.println("Role: " + role);
//        } catch (Exception e) {
//            System.out.println("Failed to decode token: " + e.getMessage());
//        }
//    }
}