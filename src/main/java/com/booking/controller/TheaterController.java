package com.booking.controller;

import com.booking.entity.Theater;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/theater")
public class TheaterController {

	@Autowired
	private TheaterRepository theaterRepository;

	// get all Theaters
	@GetMapping
	public List<Theater> getAllTheaters() {
		return this.theaterRepository.findAll();
	}

	// get Theater by id
	@GetMapping("/{id}")
	public Theater getTheaterById(@PathVariable (value = "id") long theaterId) {
		return this.theaterRepository.findById(theaterId)
				.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + theaterId));
	}

	// create Theater
	@PostMapping
	public Theater createTheater(@RequestBody Theater theater) {
		return this.theaterRepository.save(theater);
	}
	
	// update Theater
	@PutMapping("/{id}")
	public Theater updateTheater(@RequestBody Theater theater, @PathVariable ("id") long theaterId) {
		 Theater existingTheater = this.theaterRepository.findById(theaterId)
			.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + theaterId));
		 existingTheater.setName(theater.getName());
		 existingTheater.setAddress(theater.getAddress());
		 existingTheater.setPinCode(theater.getPinCode());
		 existingTheater.setScreen(theater.getScreen());
		 return this.theaterRepository.save(existingTheater);
	}
	
	// delete Theater by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Theater> deleteTheater(@PathVariable ("id") long theaterId){
		 Theater existingTheater = this.theaterRepository.findById(theaterId)
					.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + theaterId));
		 this.theaterRepository.delete(existingTheater);
		 return ResponseEntity.ok().build();
	}
}
