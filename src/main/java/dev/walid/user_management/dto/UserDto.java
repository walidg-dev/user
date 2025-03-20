package dev.walid.user_management.dto;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String email) {
}
