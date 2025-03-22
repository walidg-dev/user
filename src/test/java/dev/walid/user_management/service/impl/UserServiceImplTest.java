package dev.walid.user_management.service.impl;

import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.repository.UserRepository;
import dev.walid.user_management.repository.entity.UserEntity;
import dev.walid.user_management.service.UserService;
import dev.walid.user_management.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(repository, new UserMapper());
    }

    private static UserEntity buildUser(){

        return UserEntity.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();
    }

    @Test
    void getUserByIdShouldReturnUserFromRepository() {

        when(repository.findById(anyInt()) ).thenReturn(Mono.just(buildUser()));
        service.getById(1).subscribe(value -> {
            assertInstanceOf(UserDto.class, value);
            assertEquals("John", value.getFirstName());
            assertEquals("Doe", value.getLastName());
            assertEquals("John@email.com", value.getEmail());
            assertEquals(1, value.getId());
        });
    }

    @Test
    void getAllUsersShouldReturnUsersFromRepository() {
        var usersList = IntStream.range(0, 6).mapToObj(i -> buildUser()).toList();
        when(repository.findAll() ).thenReturn(Flux.fromIterable(usersList));

        service.getAll().subscribe(value -> {
            assertInstanceOf(UserDto.class, value);
            assertEquals("John", value.getFirstName());
            assertEquals("Doe", value.getLastName());
            assertEquals("John@email.com", value.getEmail());
            assertEquals(1, value.getId());
        });
    }

    @Test
    void createUserShouldReturnUserFromRepository() {
        UserEntity user = buildUser();
        UserDto dto = new UserMapper().toDto(user);
        when(repository.save(any()) ).thenReturn(Mono.just(user));

        service.create(dto).subscribe(value -> {
            assertInstanceOf(UserDto.class, value);
            assertEquals("John", value.getFirstName());
            assertEquals("Doe", value.getLastName());
            assertEquals("John@email.com", value.getEmail());
            assertEquals(1, value.getId());
        });
    }
    @Test
    void updateUserShouldReturnUserFromRepository() {

        UserEntity user = buildUser();
        UserDto dto = new UserMapper().toDto(user);
        when(repository.save(any()) ).thenReturn(Mono.just(user));

        service.update(1, dto).subscribe(value -> {
            assertInstanceOf(UserDto.class, value);
            assertEquals("John", value.getFirstName());
            assertEquals("Doe", value.getLastName());
            assertEquals("John@email.com", value.getEmail());
            assertEquals(1, value.getId());
        });
    }

    @Test
    void deleteUserShouldReturnVoid(){

        when(repository.deleteById(anyInt()) ).thenReturn(Mono.empty());

        var result = service.delete(1);
        StepVerifier.create(result).verifyComplete();
    }

}