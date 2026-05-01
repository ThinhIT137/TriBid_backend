package com.example.fullstackSpringBoot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fullstackSpringBoot.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

//	@Query("SELECT u from User u where u.id = :id")
//	User findById(@Param("id") String id);

	boolean existsByEmail(String email);
}
