package org.example.dto;

public class Dto {
public record LoginRequestDto(String username, String password) { }

public record LoginResponseDto(String token) { }

public record RegisterRequestDto(String username, String password, String n, String e) { }

public record SendMessageDto(String content, String receiver) { }

public record ConversationsDto(String[] conversations) { }

public record MessageDto(String content, String sender, String receiver, long timestamp) { }

public record MessagesDto(MessageDto[] messages) { }

public record UserDto(String username) { }

public record UsersDto(UserDto[] users) { }
}