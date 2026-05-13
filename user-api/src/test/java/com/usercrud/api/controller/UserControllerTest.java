package com.usercrud.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usercrud.api.domain.User;
import com.usercrud.api.exception.GlobalExceptionHandler;
import com.usercrud.api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Slice test for {@link UserController}.
 *
 * <p>Uses {@code @MockitoBean} (Spring Boot 3.4+ replacement for deprecated {@code @MockBean}).
 * {@code @Import(GlobalExceptionHandler.class)} is required because {@code @WebMvcTest}
 * does not auto-load {@code @RestControllerAdvice} beans outside the controller slice.
 */
@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // @MockitoBean replaces deprecated @MockBean (Spring Boot 3.4+)
    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser
    void getAllUsers_returns200WithList() throws Exception {
        final User user = new User(1L, "Alice", "alice@example.com", "USER");
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    @WithMockUser
    void getUserById_notFound_returns404() throws Exception {
        when(userService.getUserById(99L))
                .thenThrow(new EntityNotFoundException("User not found with id: 99"));

        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_validPayload_returns201() throws Exception {
        final User input = new User(null, "Bob", "bob@example.com", "USER");
        final User saved  = new User(2L,   "Bob", "bob@example.com", "USER");
        when(userService.createUser(any(User.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_existing_returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_notFound_returns404() throws Exception {
        doThrow(new EntityNotFoundException("User not found with id: 99"))
                .when(userService).deleteUser(99L);

        mockMvc.perform(delete("/api/v1/users/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
