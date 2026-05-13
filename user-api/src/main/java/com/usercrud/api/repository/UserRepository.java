package com.usercrud.api.repository;

import com.usercrud.api.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link User} entities.
 *
 * <p>Inherits full CRUD and pagination support from {@link JpaRepository}.
 * Satisfies FR-005, FR-013, CONTRACT-DB-01.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their unique email address.
     *
     * @param email the email to search for
     * @return an {@link Optional} containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all users ordered by the given sort specification.
     * NOTE: This method is already inherited from PagingAndSortingRepository
     * via JpaRepository; declared here for documentation clarity.
     *
     * @param sort the sort specification
     * @return list of users in the specified order
     */
    List<User> findAll(Sort sort);
}
