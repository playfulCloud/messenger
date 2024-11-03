package org.example.app;

import org.example.api.ApiService;
import org.example.dto.Dto;

import java.io.IOException;
import java.util.Scanner;


public class ConsoleMessenger {

    private static final Scanner scanner = new Scanner(System.in);
    private static ApiService apiService = new ApiService();
    private static String authToken;

    public static void main(String[] args) {
        System.out.println("=== Welcome to Console Messenger ===");
        boolean running = true;

        while (running) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. View Conversations");
            System.out.println("4. Send Message");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> viewConversations();
                case 4 -> sendMessage();
                case 5 -> logout();
                case 6 -> running = false;
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            Dto.LoginRequestDto loginRequest = new Dto.LoginRequestDto(username, password);
            Dto.LoginResponseDto response = apiService.login(loginRequest);
            authToken = response.token();
            System.out.println("Login successful! Token: " + authToken);
        } catch (IOException | InterruptedException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            Dto.RegisterRequestDto registerRequest = new Dto.RegisterRequestDto(username, password, fullName, email);
            apiService.register(registerRequest);
            System.out.println("Registration successful! You can now login.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void viewConversations() {
        if (authToken == null) {
            System.out.println("You need to login first.");
            return;
        }

        try {
            Dto.ConversationsDto conversations = apiService.getConversations(authToken);
            System.out.println("Your Conversations: " + String.join(", ", conversations.conversations()));
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to retrieve conversations: " + e.getMessage());
        }
    }

    private static void sendMessage() {
        if (authToken == null) {
            System.out.println("You need to login first.");
            return;
        }

        System.out.print("Receiver Username: ");
        String receiver = scanner.nextLine();
        System.out.print("Message Content: ");
        String content = scanner.nextLine();

        try {
            Dto.SendMessageDto message = new Dto.SendMessageDto(content, receiver);
            apiService.sendMessage(authToken, message);
            System.out.println("Message sent successfully!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }

    private static void logout() {
        authToken = null;
        System.out.println("You have been logged out.");
    }
}

