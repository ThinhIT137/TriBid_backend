package com.example.fullstackSpringBoot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fullstackSpringBoot.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

	@Modifying
	@Query("Update Auction a set a.bidAmount = a.bidAmount + :newBid where a.user.id = :userId and a.product.id = :productId")
	void addMoneyToTotal(@Param("userId") UUID userId, @Param("productId") long productId, long newBid);

}
