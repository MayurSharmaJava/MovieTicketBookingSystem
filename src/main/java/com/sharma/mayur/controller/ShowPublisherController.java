package com.sharma.mayur.controller;

import com.sharma.mayur.entity.Booking;
import com.sharma.mayur.entity.User;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/publish")
public class ShowPublisherController {

	//@PostMapping
	//public User createUser(@RequestBody User user) {
	//	return this.userRepository.save(user);
	//}

}
