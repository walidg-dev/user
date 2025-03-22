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

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static UserDto buildDto() {

        return UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("John@email.com")
                .build();

    }
    @Test
    void getUserShouldReturnUserFromService() throws Exception {

        when(service.getById(any()) ).thenReturn(Mono.just(buildDto()));
        MvcResult result = this.mockMvc.perform(get("/api/v1/users/1"))
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

        var dtoList = IntStream.range(0, 6).mapToObj(i -> buildDto()).toList();

        when(service.getAll() ).thenReturn(Flux.fromIterable(dtoList));
        MvcResult result = this.mockMvc.perform(get("/api/v1/users/all"))
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

        UserDto userDto = buildDto();
        when(service.create(any()) ).thenReturn(Mono.just(userDto));
        MvcResult result = this.mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
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

        UserDto userDto = buildDto();

        when(service.update(any(), any()) ).thenReturn(Mono.just(userDto));
        MvcResult result = this.mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
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
        MvcResult result = this.mockMvc.perform(delete("/api/v1/users/1"))
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
