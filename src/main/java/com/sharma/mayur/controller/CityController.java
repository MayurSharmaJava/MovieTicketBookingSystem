package com.sharma.mayur.controller;

import com.sharma.mayur.entity.City;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/city")
public class CityController {

	@Autowired
	private CityRepository CityRepository;

	// get all City
	@GetMapping
	public List<City> getAllCity() {
		return this.CityRepository.findAll();
	}

	// get City by id
	@GetMapping("/{id}")
	public City getCityById(@PathVariable (value = "id") long CityId) {
		return this.CityRepository.findById(CityId)
				.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + CityId));
	}

	// create City
	@PostMapping
	public City createCity(@RequestBody City City) {
		return this.CityRepository.save(City);
	}
	
	// update City
	@PutMapping("/{id}")
	public City updateCity(@RequestBody City City, @PathVariable ("id") long CityId) {
		 City existingCity = this.CityRepository.findById(CityId)
			.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + CityId));
		 existingCity.setName(City.getName());
		 return this.CityRepository.save(existingCity);
	}
	
	// delete City by id
	@DeleteMapping("/{id}")
	public ResponseEntity<City> deleteCity(@PathVariable ("id") long CityId){
		 City existingCity = this.CityRepository.findById(CityId)
					.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + CityId));
		 this.CityRepository.delete(existingCity);
		 return ResponseEntity.ok().build();
	}
}
