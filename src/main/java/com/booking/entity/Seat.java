package com.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Seat")
public class Seat {

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
	private BigDecimal price;
}
