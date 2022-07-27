package com.sharma.mayur.controller;

import com.sharma.mayur.constant.CommonConstant;
import com.sharma.mayur.entity.Booking;
import com.sharma.mayur.entity.PreBooking;
import com.sharma.mayur.entity.Seat;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/booking")
public class BookingController {


	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PreBookingController preBookingController;

	@Autowired
	private SeatController seatController;

	// get all Bookings
	@GetMapping
	public List<Booking> getAllBookings() {
		return this.bookingRepository.findAll();
	}

	// get Booking by id
	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable (value = "id") long bookingId) {
		return this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.BOOKING_NOT_FOUND_WITH_ID + bookingId));
	}

	/**
			Flow:
			put entry in prebooking table
		 	Call payment API Pass Booking Object
		 	Payment Api will call next method createBooking
		 	In create Booking will again verify that Lock is still Hold by same User before saving Booking
	 **/
	@PostMapping("preBook")
	public void preBooking(@RequestBody Booking booking) {

		//Proceed Only if Seat Available & booking is Not locked
		Optional<PreBooking> optionalPreBooking = preBookingController.getPreBookingByLockPattern(getLockPattern(booking));
		if(isSeatAvailable(booking.getSeats()) && !optionalPreBooking.isPresent()) {
			lockMySeats(getLockPattern(booking),booking.getUser().getId());
			/**
			 * Call to third party Payment API goes here
			 * Pay_BY_UPI(booking.getId(), booking.getAmount());
			 */
		}
		else{
			throw new ResourceNotFoundException("Please try after some time.");
		}
	}

	private boolean isSeatAvailable(List<Seat> requestedSeats) {
		boolean isSeatAvailable = true;
		for (Seat seat: requestedSeats) {
			Seat seatById = seatController.getSeatById(seat.getId());
			if(seatById.getStatus().equalsIgnoreCase(CommonConstant.BOOKED)){
				isSeatAvailable=false;
				break;
			}
		}
		return isSeatAvailable;
	}

	private PreBooking lockMySeats(String lockPattern, Long userId){
		PreBooking preBooking = new PreBooking();
		preBooking.setLockPattern(lockPattern);
		preBooking.setUserId(userId);
		preBooking.setLockedOn(Calendar.getInstance().getTime());
		return preBookingController.createBooking(preBooking);
	}

	private String getLockPattern(Booking booking) {

		StringBuilder seatIds=new StringBuilder();
		booking.getSeats().forEach(seat -> seatIds.append(seat.getId()));

		String showId = String.valueOf(booking.getMovieShow().getId());

		return showId+seatIds;
	}

	public void bookAndGenerateTicket(Booking booking){
		booking.getSeats().stream().forEach(seat -> seat.setStatus(CommonConstant.BOOKED));

		createBooking(booking);

		markSeatBooked(booking);

		/**
		 * generate QRCODE
		 * generate Ticket
		 * Send it over Mail / SMS / whatsapp
		 */
	}

	private void markSeatBooked(Booking booking) {
		for (Seat seat:
			 booking.getSeats()) {
			Seat seatById = seatController.getSeatById(seat.getId());
			seatById.setStatus(CommonConstant.BOOKED);
			seatController.updateSeat(seatById, seat.getId());
		}
	}

	// create Booking
	@PostMapping
	public Booking createBooking(@RequestBody Booking booking) {
		return this.bookingRepository.save(booking);
	}
	
	// update Booking
	@PutMapping("/{id}")
	public Booking updateBooking(@RequestBody Booking booking, @PathVariable ("id") long bookingId) {
		 Booking existingBooking = this.bookingRepository.findById(bookingId)
			.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.BOOKING_NOT_FOUND_WITH_ID + bookingId));
		existingBooking.setBookingDate(booking.getBookingDate());
		existingBooking.setUser(booking.getUser());
		existingBooking.setMovieShow(booking.getMovieShow());
		existingBooking.setSeats(booking.getSeats());
		existingBooking.setStatus(booking.getStatus());
		existingBooking.setAmount(booking.getAmount());
		existingBooking.setPayment(booking.getPayment());
		return this.bookingRepository.save(existingBooking);
	}
	
	// delete Booking by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Booking> deleteBooking(@PathVariable ("id") long bookingId){
		 Booking existingBooking = this.bookingRepository.findById(bookingId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.BOOKING_NOT_FOUND_WITH_ID + bookingId));
		 this.bookingRepository.delete(existingBooking);
		 return ResponseEntity.ok().build();
	}
}