package com.fitness.userservice.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data //Lombok-it will create all getters and setters
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	@Column(unique=true,nullable=false)
	private String email;
	@Column(nullable=false)
	private String password;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	public void setEmail(String email) {
		this.email=email;		
	}
	public void setPassword(String password) {
		this.password=password;	
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
}
