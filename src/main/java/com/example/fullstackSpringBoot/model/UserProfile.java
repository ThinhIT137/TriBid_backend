package com.example.fullstackSpringBoot.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {

	@Id
	private UUID id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String name;
	private String phoneNumber;
	private String address;
	private String avt;
	private String citizenIdentification;
	private long balance;

	public UserProfile(User user, String phoneNumber, String name, String address, String avt,
			String citizenIdentification, long balance) {
		super();
		this.user = user;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.address = address;
		this.avt = avt;
		this.citizenIdentification = citizenIdentification;
		this.balance = balance;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvt() {
		return avt;
	}

	public void setAvt(String avt) {
		this.avt = avt;
	}

	public String getCitizenIdentification() {
		return citizenIdentification;
	}

	public void setCitizenIdentification(String citizenIdentification) {
		this.citizenIdentification = citizenIdentification;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

}
