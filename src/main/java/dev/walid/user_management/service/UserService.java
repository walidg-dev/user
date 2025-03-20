package dev.walid.user_management.service;

import dev.walid.user_management.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> getAll();

    Mono<UserDto> getById(Integer id);

    Mono<UserDto> create(UserDto user);

    Mono<UserDto> update(Integer id, UserDto user);

    Mono<Void> delete(Integer id);
}
