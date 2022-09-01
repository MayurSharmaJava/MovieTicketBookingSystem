package com.booking.controller;

import com.booking.util.CommonUtil;
import com.booking.util.PriceCalculator;
import com.booking.constant.CommonConstant;
import com.booking.entity.Booking;
import com.booking.entity.PreBooking;
import com.booking.entity.Seat;
import com.booking.exception.ResourceNotFoundException;
import com.booking.pojo.PreBookModel;
import com.booking.pojo.TicketModel;
import com.booking.repository.BookingRepository;
import com.booking.strategy.NoDiscountStrategy;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController @RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PreBookingController preBookingController;

	@Autowired
	private SeatController seatController;

	@Autowired
	private EntityManager entityManager;

	@PostMapping("pre-booking")
	public PreBookModel preBooking(@RequestBody Booking booking) {

		//Proceed Only if Seat Available & booking is Not locked
		String lockPattern = CommonUtil.getLockPattern(booking);

		Optional<PreBooking> optionalPreBooking = preBookingController.getPreBookingByLockPattern(lockPattern);

		boolean isSeatAvailable = seatController.isSeatAvailable(booking.getSeats());

		if(!optionalPreBooking.isPresent() &&  isSeatAvailable) {
			/** Locking for 10 min **/
			preBookingController.lockMySeats(lockPattern,booking.getUser().getId());

			booking.setStatus(CommonConstant.PENDING);

			/** Will not trust on UI input for Seat price fetching again from DB **/
			List<Seat> seats = booking.getSeats().stream().map(
					seat -> seatController.getSeatById(seat.getId())
			).collect(Collectors.toList());
			booking.setSeats(seats);

			PriceCalculator calculator = new PriceCalculator(new NoDiscountStrategy());/** Strategy Pattern **/
			BigDecimal calculatedAmount = calculator.calculate(booking);
			booking.setAmount(calculatedAmount);

			booking.getPayment().setAmount(calculatedAmount);
			booking.getPayment().setStatus(CommonConstant.PAYMENT_PENDING);

			/** Booking saved with Pending Status **/

			Booking save = bookingRepository.save(booking);

			/**
			 * Call to third party Payment API goes here
			 * Pay_BY_UPI(booking.getId(), booking.getAmount());
			 */

			/** This Transaction/Booking ID can be shown on UI in case of any issue **/
			PreBookModel preBookModel = new PreBookModel(save.getId(),save.getPayment().getTransactionNo());

			Link paymentStatusLink = WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(PaymentController.class).getPaymentById(preBookModel.getTransactionId())
			).withRel("payment-status");
			preBookModel.add(paymentStatusLink);

			return preBookModel;
		}
		else{
			throw new ResourceNotFoundException("Please try after some time.");
		}
	}

	public long bookTicket(String transactionNo){

		Session session = entityManager.unwrap(Session.class);

		String hql = "FROM Booking B WHERE B.payment.transactionNo = :transactionNo";

		Query<Booking> query = session.createQuery(hql);
		query.setParameter("transactionNo", transactionNo);
		Booking booking = query.getSingleResult();

		booking.setStatus(CommonConstant.BOOKED);
		booking.getSeats().stream().forEach(seat -> seat.setStatus(CommonConstant.BOOKED));

		/** Actual Booking and Seat are Booked Here **/
		return createBooking(booking).getId();

		 /** TODO: send Event which will be listened by below
		 * generate QRCODE
		 * generate Ticket
		 * Send it over Mail
		 * Send it over SMS
		 * Send it over whatsapp
		 */

	}

	@GetMapping("/{id}")
	public TicketModel generateTicket(@PathVariable (value = "id") long bookingId) {

		Booking booking = getBookingById(bookingId);

		TicketModel ticketModel = new TicketModel();
		ticketModel.setId(booking.getId());
		ticketModel.setAmount(booking.getAmount());
		ticketModel.setSeats(booking.getSeats());
		ticketModel.setMovieName(booking.getMovieShow().getMovie().getName());
		ticketModel.setStartTime(booking.getMovieShow().getStartTime());
		ticketModel.setEndTime(booking.getMovieShow().getEndTime());
		ticketModel.setTheaterName(booking.getMovieShow().getTheater().getName());
		ticketModel.setScreen(booking.getMovieShow().getScreen().getName());

		Link imdbLink = Link.of("https://www.imdb.com/title/"+ booking.getMovieShow().getMovie().getImdbNumber(), LinkRelation.of("Movie Details"));
		ticketModel.add(imdbLink);

		Link theaterLink = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(TheaterController.class).getTheaterById(booking.getMovieShow().getTheater().getId())
		).withRel("Theater Details");
		ticketModel.add(theaterLink);

		return ticketModel;
	}

	public Booking getBookingById(@PathVariable (value = "id") long bookingId) {
		return this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.BOOKING_NOT_FOUND_WITH_ID + bookingId));
	}

	@Hidden
	@PostMapping
	public Booking createBooking(@RequestBody Booking booking) {
		return this.bookingRepository.save(booking);
	}

	@Hidden
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

	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<Booking> deleteBooking(@PathVariable ("id") long bookingId){
		 Booking existingBooking = this.bookingRepository.findById(bookingId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.BOOKING_NOT_FOUND_WITH_ID + bookingId));
		 this.bookingRepository.delete(existingBooking);
		 return ResponseEntity.ok().build();
	}

}
