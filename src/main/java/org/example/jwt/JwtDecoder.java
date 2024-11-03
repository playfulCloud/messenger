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
}