package com.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

	enum STATUS {
		PENDING,
		SUCCESS,
		FAILED,
		REFUND
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "payment_sequence")
	private long id;
	
	@Column(name = "transactionNo")
	private String transactionNo;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "status")
	private String status;
}
