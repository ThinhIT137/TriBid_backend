package com.example.fullstackSpringBoot.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String email;
	private String password;
	private String role; // Admin, User
	private boolean status;
	@CreationTimestamp
	private LocalDateTime createdAt;
	private String keyResetPassword;
	@CreationTimestamp
	private LocalDateTime resetPasswordAt;

	// FK
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> product = new ArrayList<Product>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefreshToken> refreshTokens = new ArrayList<RefreshToken>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Auction> auctions = new ArrayList<Auction>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AuctionBid> auctionBidcs = new ArrayList<AuctionBid>();
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserProfile userProfile;

	public User(String email, String password, LocalDateTime createdAt) {
		super();
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.role = "User";
		this.status = true;
		keyResetPassword = "";
		resetPasswordAt = null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<RefreshToken> getRefreshTokens() {
		return refreshTokens;
	}

	public void setRefreshTokens(List<RefreshToken> refreshTokens) {
		this.refreshTokens = refreshTokens;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	public List<AuctionBid> getAuctionBidcs() {
		return auctionBidcs;
	}

	public void setAuctionBidcs(List<AuctionBid> auctionBidcs) {
		this.auctionBidcs = auctionBidcs;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getKeyResetPassword() {
		return keyResetPassword;
	}

	public void setKeyResetPassword(String keyResetPassword) {
		this.keyResetPassword = keyResetPassword;
	}

	public LocalDateTime getResetPasswordAt() {
		return resetPasswordAt;
	}

	public void setResetPasswordAt(LocalDateTime resetPasswordAt) {
		this.resetPasswordAt = resetPasswordAt;
	}

}
