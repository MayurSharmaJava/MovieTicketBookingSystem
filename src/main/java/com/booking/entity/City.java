package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "city")
@NoArgsConstructor
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "city_sequence")
	private long id;

	@Column(name = "name")
	private String name;

}
