package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.Seat;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.SeatRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;


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

	public boolean isSeatAvailable(List<Seat> requestedSeats) {
		boolean isSeatAvailable = true;
		for (Seat seat: requestedSeats) {
			Seat seatById = getSeatById(seat.getId());
			if(seatById.getStatus().equalsIgnoreCase(CommonConstant.BOOKED)){
				isSeatAvailable=false;
				break;
			}
		}
		return isSeatAvailable;
	}
}
