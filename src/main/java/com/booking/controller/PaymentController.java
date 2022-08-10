package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.Payment;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {


	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BookingController bookingController;

	@PostMapping("/status")
	public void paymentStatus(@RequestBody Payment payment) {
		this.paymentRepository.save(payment);
		bookingController.bookAndGenerateTicket(payment);
	}

	// get all Payments
	@GetMapping
	public List<Payment> getAllPayments() {
		return this.paymentRepository.findAll();
	}

	// get Payment by id
	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable (value = "id") long paymentId) {
		return this.paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.PAYMENT_NOT_FOUND_WITH_ID + paymentId));
	}

	// create Payment
	@PostMapping
	public Payment createPayment(@RequestBody Payment payment) {
		return this.paymentRepository.save(payment);
	}
	
	// update Payment
	@PutMapping("/{id}")
	public Payment updatePayment(@RequestBody Payment payment, @PathVariable ("id") long paymentId) {
		 Payment existingPayment = this.paymentRepository.findById(paymentId)
			.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.PAYMENT_NOT_FOUND_WITH_ID + paymentId));
		 existingPayment.setTransactionNo(payment.getTransactionNo());
		 existingPayment.setType(payment.getType());
		 existingPayment.setAmount(payment.getAmount());
		 existingPayment.setStatus(payment.getStatus());
		 return this.paymentRepository.save(existingPayment);
	}
	
	// delete Payment by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Payment> deletePayment(@PathVariable ("id") long paymentId){
		 Payment existingPayment = this.paymentRepository.findById(paymentId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.PAYMENT_NOT_FOUND_WITH_ID + paymentId));
		 this.paymentRepository.delete(existingPayment);
		 return ResponseEntity.ok().build();
	}
}
