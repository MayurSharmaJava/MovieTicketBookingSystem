package com.sharma.mayur.controller;

import com.sharma.mayur.entity.Theater;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.TheaterRepository;
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
	public Theater getTheaterById(@PathVariable (value = "id") long TheaterId) {
		return this.theaterRepository.findById(TheaterId)
				.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + TheaterId));
	}

	// create Theater
	@PostMapping
	public Theater createTheater(@RequestBody Theater Theater) {
		return this.theaterRepository.save(Theater);
	}
	
	// update Theater
	@PutMapping("/{id}")
	public Theater updateTheater(@RequestBody Theater Theater, @PathVariable ("id") long TheaterId) {
		 Theater existingTheater = this.theaterRepository.findById(TheaterId)
			.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + TheaterId));
		 existingTheater.setName(Theater.getName());
		 existingTheater.setAddress(Theater.getAddress());
		 existingTheater.setPinCode(Theater.getPinCode());
		 existingTheater.setScreen(Theater.getScreen());
		 return this.theaterRepository.save(existingTheater);
	}
	
	// delete Theater by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Theater> deleteTheater(@PathVariable ("id") long TheaterId){
		 Theater existingTheater = this.theaterRepository.findById(TheaterId)
					.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + TheaterId));
		 this.theaterRepository.delete(existingTheater);
		 return ResponseEntity.ok().build();
	}
}
