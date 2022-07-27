package com.sharma.mayur.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_sequence")
	private long id;
	
	@Column(name = "bookingDate")
	private Date bookingDate;
	
	@OneToOne
	private User user;

	@OneToOne
	@JoinColumn
	private MovieShow movieShow;

	@OneToMany
	@JoinColumn(name = "booking_id")
	private List<Seat> seats;

	@Column(name = "status")
	private String status;

	@Column(name = "amount")
	private double amount;

	@OneToOne
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Payment payment;
}