package com.usercrud.api.controller;

import com.usercrud.api.domain.User;
import com.usercrud.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing user CRUD endpoints under {@code /api/v1/users}.
 *
 * <p>Satisfies FR-005, FR-013, CONTRACT-API-01.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor injection of {@link UserService}.
     *
     * @param userService the user service
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/v1/users — list all users.
     *
     * @return 200 OK with list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /api/v1/users/{id} — get a user by id.
     *
     * @param id the user id
     * @return 200 OK with the user, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * POST /api/v1/users — create a new user.
     *
     * @param user the user to create
     * @return 201 Created with the persisted user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody final User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    /**
     * PUT /api/v1/users/{id} — update an existing user.
     *
     * @param id   the user id
     * @param user the updated user details
     * @return 200 OK with the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable final Long id,
                                           @Valid @RequestBody final User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    /**
     * DELETE /api/v1/users/{id} — delete a user.
     *
     * @param id the user id
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
