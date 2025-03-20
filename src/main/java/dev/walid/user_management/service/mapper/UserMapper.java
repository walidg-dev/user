package dev.walid.user_management.service.mapper;

import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity user){
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    public UserEntity toUser(UserDto dto){
        return UserEntity.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UserEntity toUser(Integer id, UserDto dto){
        UserEntity user = toUser(dto);
        user.setId(id);
        return user;
    }

}
