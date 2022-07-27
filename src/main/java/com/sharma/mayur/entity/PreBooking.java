package com.sharma.mayur.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "PreBookingLock")
public class PreBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "preBookingLock_sequence")
	private long id;

	@Column(name = "lockPattern")
	private String lockPattern;

	@Column(name = "lockedByUser")
	private Long userId;

	@Column(name = "lockedOn")
	private Date lockedOn;

}
