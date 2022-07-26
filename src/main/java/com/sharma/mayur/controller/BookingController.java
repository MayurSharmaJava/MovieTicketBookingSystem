package com.sharma.mayur.controller;

import com.sharma.mayur.entity.Booking;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/Booking")
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	// get all Bookings
	@GetMapping
	public List<Booking> getAllBookings() {
		return this.bookingRepository.findAll();
	}

	// get Booking by id
	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable (value = "id") long BookingId) {
		return this.bookingRepository.findById(BookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id :" + BookingId));

	}

	// create Booking
	@PostMapping
	public Booking createBooking(@RequestBody Booking Booking) {

		return this.bookingRepository.save(Booking);
	}
	
	// update Booking
	@PutMapping("/{id}")
	public Booking updateBooking(@RequestBody Booking Booking, @PathVariable ("id") long BookingId) {
		 Booking existingBooking = this.bookingRepository.findById(BookingId)
			.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id :" + BookingId));
		existingBooking.setBookingDate(Booking.getBookingDate());
		existingBooking.setUser(Booking.getUser());
		existingBooking.setMovieShow(Booking.getMovieShow());
		existingBooking.setSeats(Booking.getSeats());
		existingBooking.setStatus(Booking.getStatus());
		existingBooking.setAmount(Booking.getAmount());
		existingBooking.setPayment(Booking.getPayment());
		return this.bookingRepository.save(existingBooking);
	}
	
	// delete Booking by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Booking> deleteBooking(@PathVariable ("id") long BookingId){
		 Booking existingBooking = this.bookingRepository.findById(BookingId)
					.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id :" + BookingId));
		 this.bookingRepository.delete(existingBooking);
		 return ResponseEntity.ok().build();
	}
}
