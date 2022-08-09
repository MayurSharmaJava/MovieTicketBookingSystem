package com.booking.controller;

import com.booking.entity.MovieShow;
import com.booking.repository.TheaterRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	private TheaterRepository theaterRepository;





}
