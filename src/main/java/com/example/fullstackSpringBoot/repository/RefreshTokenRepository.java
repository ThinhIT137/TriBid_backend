package com.example.fullstackSpringBoot.repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fullstackSpringBoot.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	@Query("SELECT r FROM RefreshToken r WHERE r.user.id = :userId AND r.status = true")
	Optional<ArrayList<RefreshToken>> findAllValidTokensByUser(@Param("userId") UUID userId);

//	@Query("SELECT r FROM RefreshToken r WHERE r.token = :token and r.user.id = :userId")
	Optional<RefreshToken> findByTokenAndUser_Id(String token, UUID userId);

	Optional<RefreshToken> findTopByUser_idAndStatusTrue(UUID userId);

	Optional<RefreshToken> findTopByUser_idAndStatusTrueOrderByCreatedAtDesc(UUID userId);

	@Modifying
	@Query("UPDATE RefreshToken r SET r.status = false WHERE r.user.id = :userId AND r.status = true")
	void revokeAllTokensByUserId(@Param("userId") UUID userId);
}
