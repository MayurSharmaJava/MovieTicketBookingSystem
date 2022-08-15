package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.City;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/city")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@GetMapping
	public List<City> getAllCity() {
		return this.cityRepository.findAll();
	}

	@GetMapping("/{id}")
	public City getCityById(@PathVariable (value = "id") long cityId) {
		return this.cityRepository.findById(cityId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID + cityId));
	}

	@PostMapping
	public City createCity(@RequestBody City city) {
		return this.cityRepository.save(city);
	}
	
	@PutMapping("/{id}")
	public City updateCity(@RequestBody City city, @PathVariable ("id") long cityId) {
		 City existingCity = this.cityRepository.findById(cityId)
			.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID + cityId));
		 existingCity.setName(city.getName());
		 return this.cityRepository.save(existingCity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<City> deleteCity(@PathVariable ("id") long cityId){
		 City existingCity = this.cityRepository.findById(cityId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID+ cityId));
		 this.cityRepository.delete(existingCity);
		 return ResponseEntity.ok().build();
	}
}
