package com.sharma.mayur.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "seat")
public class Seat {
	enum SEAT_TYPE {
		Economy,
		Premium,
		VIP
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seat_sequence")
	private long id;

	@Column(name = "seatNumber")
	private String seatNumber;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "price")
	private double price;
}
