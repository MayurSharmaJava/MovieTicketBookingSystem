package com.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Movie")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "movie_sequence")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "imdbNumber")
	private String imdbNumber;

}
