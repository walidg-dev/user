package dev.walid.user_management.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
