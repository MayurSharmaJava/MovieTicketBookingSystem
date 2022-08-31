package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "users_sequence")
	private long id;

	@Column(name = "username")
	private String username;

	@JsonIgnore
	@Column(name = "password",
			length = 100)
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<Authority> authorities;
}
