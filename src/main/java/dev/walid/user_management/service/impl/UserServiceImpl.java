package dev.walid.user_management.service.impl;

import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.repository.UserRepository;
import dev.walid.user_management.service.UserService;
import dev.walid.user_management.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public Flux<UserDto> getAll(){
        return repository.findAll().map(mapper::toDto);
    }

    public Mono<UserDto> getById(Integer id){
        return repository.findById(id).map(mapper::toDto);
    }

    public Mono<UserDto> create(UserDto dto){
        return repository.save(mapper.toUser(dto)).map(mapper::toDto);
    }

    public Mono<UserDto> update(Integer id, UserDto dto){
        return repository.save(mapper.toUser(id, dto)).map(mapper::toDto);
    }

    public Mono<Void> delete(Integer id){
        return repository.deleteById(id);
    }
}
