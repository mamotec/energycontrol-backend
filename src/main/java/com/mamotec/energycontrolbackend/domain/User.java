package com.mamotec.energycontrolbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "mamotec_user")
public class User extends BaseEntity {

	// region Fields

	@Column(name = "first_name", nullable = false)
	@NonNull
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@NonNull
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	@NonNull
	private String email;

	@Column(name = "username", nullable = false, unique = true)
	@NonNull
	private String username;

	@Column(name = "password", nullable = false)
	@NonNull
	private String password;

	// endregion

}
