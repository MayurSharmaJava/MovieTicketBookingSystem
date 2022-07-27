package com.sharma.mayur.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "address_sequence")
	private long id;

	@Column(name = "addressLine1")
	private String addressLine1;

	@Column(name = "addressLine2")
	private String addressLine2;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

}
