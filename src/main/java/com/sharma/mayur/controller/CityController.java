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
	private CityRepository cityRepository;

	// get all City
	@GetMapping
	public List<City> getAllCity() {
		return this.cityRepository.findAll();
	}

	// get City by id
	@GetMapping("/{id}")
	public City getCityById(@PathVariable (value = "id") long cityId) {
		return this.cityRepository.findById(cityId)
				.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + cityId));
	}

	// create City
	@PostMapping
	public City createCity(@RequestBody City city) {
		return this.cityRepository.save(city);
	}
	
	// update City
	@PutMapping("/{id}")
	public City updateCity(@RequestBody City city, @PathVariable ("id") long cityId) {
		 City existingCity = this.cityRepository.findById(cityId)
			.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + cityId));
		 existingCity.setName(city.getName());
		 return this.cityRepository.save(existingCity);
	}
	
	// delete City by id
	@DeleteMapping("/{id}")
	public ResponseEntity<City> deleteCity(@PathVariable ("id") long cityId){
		 City existingCity = this.cityRepository.findById(cityId)
					.orElseThrow(() -> new ResourceNotFoundException("City not found with id :" + cityId));
		 this.cityRepository.delete(existingCity);
		 return ResponseEntity.ok().build();
	}
}