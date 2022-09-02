package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.User;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@RequestMapping("/")
public class HomeController {

	@GetMapping
	public List<User> getHomePage() {
		return "Movie Ticket Booking System";
	}

}
