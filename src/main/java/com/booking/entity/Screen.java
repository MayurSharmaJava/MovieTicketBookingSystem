package com.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "screens")
public class Screen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "screen_sequence")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "capacity")
	private int capacity;
}
