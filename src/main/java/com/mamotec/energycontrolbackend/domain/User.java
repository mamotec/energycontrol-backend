package com.mamotec.energycontrolbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "mamotec_user")
public class User extends BaseEntity implements UserDetails {

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

	@Enumerated(EnumType.ORDINAL)
	private Role role;

	// endregion

	// region Overwrite Methods
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	// endregion

}
