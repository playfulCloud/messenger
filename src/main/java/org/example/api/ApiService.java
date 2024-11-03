package org.example.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Dto;


public class ApiService {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Dto.LoginResponseDto login(Dto.LoginRequestDto loginRequest) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(loginRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Dto.LoginResponseDto.class); // Parsowanie odpowiedzi do LoginResponseDto
        } else {
            throw new RuntimeException("Login failed with status code: " + response.statusCode());
        }
    }

    public void register(Dto.RegisterRequestDto registerRequest) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(registerRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Registration failed with status code: " + response.statusCode());
        }
    }

    public void sendMessage(String authToken, Dto.SendMessageDto sendMessageRequest) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(sendMessageRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/message/conversations"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Send message failed with status code: " + response.statusCode());
        }
    }

    public Dto.ConversationsDto getConversations(String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/message/conversations"))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Dto.ConversationsDto.class); // Parsowanie odpowiedzi do ConversationsDto
        } else {
            throw new RuntimeException("Get conversations failed with status code: " + response.statusCode());
        }
    }

    public Dto.MessagesDto getConversationMessages(String authToken, String participant) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/message/conversations/" + participant))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Dto.MessagesDto.class); // Parsowanie odpowiedzi do MessagesDto
        } else {
            throw new RuntimeException("Get conversation messages failed with status code: " + response.statusCode());
        }
    }

    public Dto.UsersDto getUsers(String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Dto.UsersDto.class); // Parsowanie odpowiedzi do UsersDto
        } else {
            throw new RuntimeException("Get users failed with status code: " + response.statusCode());
        }
    }
}
