package dev.walid.user_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.walid.user_management.dto.UserDto;
import dev.walid.user_management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserShouldReturnUserFromService() throws Exception {

        UserDto dto = UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();

        when(service.getById(any()) ).thenReturn(Mono.just(dto));
        MvcResult result = this.mockMvc.perform(get("/users/1"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.lastName").value("Doe"),
                        jsonPath("$.firstName").value("John"),
                        jsonPath("$.email").value("John@email.com"),
                        jsonPath("$.id").value(1)
                )
                .andDo(print());
    }

    @Test
    void getUsersShouldReturnUsersFromService() throws Exception {

        UserDto dto = UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();

        var dtoList = IntStream.range(0, 6).mapToObj(i -> dto).toList();

        when(service.getAll() ).thenReturn(Flux.fromIterable(dtoList));
        MvcResult result = this.mockMvc.perform(get("/users/all"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(asJsonString(dtoList))
                )
                .andDo(print());
    }

    @Test
    void createUserShouldReturnUserFromService() throws Exception {

        UserDto dto = UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();

        when(service.create(any()) ).thenReturn(Mono.just(dto));
        MvcResult result = this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.lastName").value("Doe"),
                        jsonPath("$.firstName").value("John"),
                        jsonPath("$.email").value("John@email.com"),
                        jsonPath("$.id").value(1)
                )
                .andDo(print());
    }

    @Test
    void updateUserShouldReturnUserFromService() throws Exception {

        UserDto dto = UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();

        when(service.update(any(), any()) ).thenReturn(Mono.just(dto));
        MvcResult result = this.mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.lastName").value("Doe"),
                        jsonPath("$.firstName").value("John"),
                        jsonPath("$.email").value("John@email.com"),
                        jsonPath("$.id").value(1)
                )
                .andDo(print());
    }

    @Test
    void deleteUserShouldReturnVoid() throws Exception {

        when(service.delete(any()) ).thenReturn(Mono.empty());
        MvcResult result = this.mockMvc.perform(delete("/users/1"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpectAll(
                        status().isOk(),
                        content().string("")
                )
                .andDo(print());
    }


}
