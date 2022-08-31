package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.Payment;
import com.booking.exception.ResourceNotFoundException;
import com.booking.pojo.PaymentModel;
import com.booking.pojo.TicketModel;
import com.booking.repository.PaymentRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {


	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BookingController bookingController;

	/** TODO: Need to think flow again on this method as this payment method is returning ticket **/
	@PostMapping("/status")
	public PaymentModel paymentStatus(@RequestBody Payment paymentIn) {
		List<Payment> paymentList = getPaymentById(paymentIn.getTransactionNo());
		if(!paymentList.isEmpty()) {
			Payment payment = paymentList.get(0);
			payment.setStatus(paymentIn.getStatus());
			payment = this.paymentRepository.save(payment);

			if (CommonConstant.PAYMENT_SUCCESS.equalsIgnoreCase(paymentIn.getStatus())) {
				long bookingId = bookingController.bookTicket(payment.getTransactionNo());

				PaymentModel paymentModel = new PaymentModel(payment.getId(),payment.getTransactionNo(),payment.getType(),payment.getAmount(),payment.getStatus());
				Link autoRedirectLink = WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookingController.class).generateTicket(bookingId)
				).withRel("autoRedirect");
				return paymentModel.add(autoRedirectLink);
			}
		}
		else{
			throw new ResourceNotFoundException(CommonConstant.PAYMENT_NOT_FOUND_WITH_ID + paymentIn.getTransactionNo());
		}
		return null;
	}

	@GetMapping("/{txnId}")
	public List<Payment> getPaymentById(@PathVariable (value = "txnId") String txnId) {
		return this.paymentRepository.findByTransactionNo(txnId);
	}

	@Hidden
	@PostMapping
	public Payment createPayment(@RequestBody Payment payment) {
		return this.paymentRepository.save(payment);
	}

	@Hidden
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
}
