package com.usercrud.api.service;

import com.usercrud.api.domain.User;
import com.usercrud.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for user CRUD operations.
 *
 * <p>Encapsulates business logic and transaction boundaries.
 * Satisfies FR-005, FR-013, BC-001, BC-002, BC-003.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor injection of {@link UserRepository}.
     *
     * @param userRepository the user repository
     */
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all users sorted by name ascending.
     *
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    /**
     * Retrieve a user by id.
     *
     * @param id the user id
     * @return the user
     * @throws EntityNotFoundException if no user with the given id exists
     */
    @Transactional(readOnly = true)
    public User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return the persisted user with generated id
     */
    public User createUser(final User user) {
        return userRepository.save(user);
    }

    /**
     * Update an existing user.
     *
     * @param id          the id of the user to update
     * @param userDetails the updated user details
     * @return the updated user
     * @throws EntityNotFoundException if no user with the given id exists
     */
    public User updateUser(final Long id, final User userDetails) {
        final User existing = getUserById(id);
        existing.setName(userDetails.getName());
        existing.setEmail(userDetails.getEmail());
        existing.setRole(userDetails.getRole());
        return userRepository.save(existing);
    }

    /**
     * Delete a user by id.
     *
     * @param id the id of the user to delete
     * @throws EntityNotFoundException if no user with the given id exists
     */
    public void deleteUser(final Long id) {
        final User entity = getUserById(id);
        userRepository.delete(entity);
    }
}
