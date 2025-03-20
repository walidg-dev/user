package dev.walid.user_management.service.mapper;

import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public UserEntity toUser(UserDto dto){
        return UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UserEntity toUser(Integer id, UserDto dto){
        UserEntity user = toUser(dto);
        user.setId(id);
        return user;
    }

}
