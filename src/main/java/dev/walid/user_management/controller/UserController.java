package dev.walid.user_management.controller;

import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    Mono<UserDto> getUser(@PathVariable("id") Integer id){
        return service.getById(id);
    }

    @GetMapping("/all")
    Flux<UserDto> getUsers(){
        return service.getAll();
    }

    @PostMapping
    Mono<UserDto> createUser(@RequestBody UserDto userDto){
        return service.create(userDto);
    }

    @PutMapping("/{id}")
    Mono<UserDto> updateUser(@PathVariable("id") Integer id, @RequestBody UserDto userDto){
        return service.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteUser(@PathVariable("id") Integer id){
        return service.delete(id);
    }


}
