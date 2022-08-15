package com.booking.controller;

import com.booking.entity.Seat;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;


@RestController
@RequestMapping("/api/seats")
public class SeatController {

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private EntityManager entityManager;

	@GetMapping("/{id}")
	public Seat getSeatById(@PathVariable (value = "id") long seatId) {
		return this.seatRepository.findById(seatId)
				.orElseThrow(() -> new ResourceNotFoundException("Seat not found with id :" + seatId));
	}

	@PostMapping
	public Seat createSeat(@RequestBody Seat seat) {
		return this.seatRepository.save(seat);
	}
	
	@PutMapping("/{id}")
	public Seat updateSeat(@RequestBody Seat seat, @PathVariable ("id") long seatId) {
		 Seat existingSeat = this.seatRepository.findById(seatId)
			.orElseThrow(() -> new ResourceNotFoundException("Seat not found with id :" + seatId));
		 seat.setStatus(seat.getStatus());
		 //--Updating Only Seat Status Price Show Mapping
		 return this.seatRepository.save(existingSeat);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Seat> deleteSeat(@PathVariable ("id") long userId){
		 Seat existingSeat = this.seatRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("Seat not found with id :" + userId));
		 this.seatRepository.delete(existingSeat);
		 return ResponseEntity.ok().build();
	}
}
