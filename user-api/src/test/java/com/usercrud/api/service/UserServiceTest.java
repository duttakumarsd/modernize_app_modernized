package com.usercrud.api.service;

import com.usercrud.api.domain.User;
import com.usercrud.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User alice;

    @BeforeEach
    void setUp() {
        alice = new User(1L, "Alice", "alice@example.com", "USER");
    }

    @Test
    void getAllUsers_returnsSortedList() {
        when(userRepository.findAll(any(Sort.class))).thenReturn(List.of(alice));
        final List<User> result = userService.getAllUsers();
        assertThat(result).hasSize(1).first().extracting(User::getName).isEqualTo("Alice");
    }

    @Test
    void getUserById_found_returnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(alice));
        assertThat(userService.getUserById(1L).getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void getUserById_notFound_throwsEntityNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteUser_callsRepositoryDelete() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(alice));
        userService.deleteUser(1L);
        // Verify delete(entity) is called, NOT deleteById — avoids double query anti-pattern
        verify(userRepository, times(1)).delete(alice);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void createUser_savesAndReturnsUser() {
        when(userRepository.save(any(User.class))).thenReturn(alice);
        final User result = userService.createUser(alice);
        assertThat(result.getId()).isEqualTo(1L);
    }
}
