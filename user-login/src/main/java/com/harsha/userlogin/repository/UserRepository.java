package com.harsha.userlogin.repository;

import com.harsha.userlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("""
        SELECT u FROM User u 
        LEFT JOIN FETCH u.roles r 
        LEFT JOIN FETCH r.permissions 
        WHERE u.email = :email
    """)
    Optional<User> findByEmailWithRolesAndPermissions(String email);
}
