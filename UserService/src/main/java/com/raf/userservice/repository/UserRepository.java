package com.raf.userservice.repository;

import com.raf.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> deleteUserById(Long id);

    Optional<User> findUserById(Long id);

}
