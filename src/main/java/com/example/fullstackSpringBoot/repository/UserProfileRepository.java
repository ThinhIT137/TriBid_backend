package com.example.fullstackSpringBoot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fullstackSpringBoot.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
	Optional<UserProfile> findById(UUID id);
}
