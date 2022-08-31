package com.booking.controller;

import com.booking.entity.PreBooking;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.PreBookingRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api/pre-booking-lock")
public class PreBookingController {

	@Autowired
	private PreBookingRepository preBookingRepository;

	@Autowired
	private EntityManager entityManager;

	//--TODO Pagination
	@GetMapping
	public List<PreBooking> getAllPreBookings() {
		return this.preBookingRepository.findAll();
	}

	@Hidden
	@GetMapping("/{lockPattern}")
	public Optional<PreBooking> getPreBookingByLockPattern(
				@PathVariable (value = "lockPattern") String lockPattern) {
		Session session = entityManager.unwrap(Session.class);

		String hql = "FROM PreBooking S WHERE S.lockPattern = :lockPattern";

		Query<PreBooking> query = session.createQuery(hql);
		query.setParameter("lockPattern",lockPattern);

		try {
			List<PreBooking> resultList = query.getResultList();
			if(!resultList.isEmpty()) {
				return Optional.of(resultList.get(0));
			}
		}catch (NoSuchElementException exp){
			return Optional.empty();
		}
		return Optional.empty();
	}

	@Hidden
	@GetMapping("/{id}")
	public PreBooking getBookingById(@PathVariable (value = "id") long bookingId) {
		return this.preBookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("PreBooking not found with id :" + bookingId));

	}

	@Hidden
	@PostMapping
	public PreBooking createPreBooking(@RequestBody PreBooking preBooking) {
		return this.preBookingRepository.save(preBooking);
	}

	@Hidden
	@PutMapping("/{id}")
	public PreBooking updateBooking(@RequestBody PreBooking preBooking, @PathVariable ("id") long bookingId) {
		 PreBooking existingBooking = this.preBookingRepository.findById(bookingId)
			.orElseThrow(() -> new ResourceNotFoundException("PreBooking not found with id :" + bookingId));
		existingBooking.setLockPattern(preBooking.getLockPattern());
		existingBooking.setUserId(preBooking.getUserId());
		existingBooking.setLockedOn(preBooking.getLockedOn());
		return this.preBookingRepository.save(existingBooking);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PreBooking> deleteBooking(@PathVariable ("id") long bookingId){
		 PreBooking existingBooking = this.preBookingRepository.findById(bookingId)
					.orElseThrow(() -> new ResourceNotFoundException("PreBooking not found with id :" + bookingId));
		 this.preBookingRepository.delete(existingBooking);
		 return ResponseEntity.ok().build();
	}

	public PreBooking lockMySeats(String lockPattern, Long userId){
		PreBooking preBooking = new PreBooking();
		preBooking.setLockPattern(lockPattern);
		preBooking.setUserId(userId);
		preBooking.setLockedOn(Calendar.getInstance().getTime());
		return createPreBooking(preBooking);
	}
}
