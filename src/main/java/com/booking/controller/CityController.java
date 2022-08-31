package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.City;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.CityRepository;
import io.swagger.v3.oas.annotations.Hidden;
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

	@Hidden
	@GetMapping("/{id}")
	public City getCityById(@PathVariable (value = "id") long cityId) {
		return this.cityRepository.findById(cityId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID + cityId));
	}

	@PostMapping
	public City createCity(@RequestParam ("city name") String cityName) {
		City city = new City();
		city.setName(cityName);
		return this.cityRepository.save(city);
	}

	@Hidden
	@PutMapping("/{id}")
	public City updateCity(@RequestBody City city, @PathVariable ("id") long cityId) {
		 City existingCity = this.cityRepository.findById(cityId)
			.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID + cityId));
		 existingCity.setName(city.getName());
		 return this.cityRepository.save(existingCity);
	}

	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<City> deleteCity(@PathVariable ("id") long cityId){
		 City existingCity = this.cityRepository.findById(cityId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.CITY_NOT_FOUND_WITH_ID+ cityId));
		 this.cityRepository.delete(existingCity);
		 return ResponseEntity.ok().build();
	}
}
