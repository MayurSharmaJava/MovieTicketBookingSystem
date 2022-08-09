package com.booking.controller;

import com.booking.entity.MovieShow;
import com.booking.entity.Theater;
import com.booking.exception.ResourceNotFoundException;
import com.booking.pojo.TheaterModel;
import com.booking.repository.TheaterRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private EntityManager entityManager;

	// get Booking by id
	@GetMapping
	public List<TheaterModel> getTheater(
			@RequestParam ("movie_id") long movieId,
			@RequestParam (value = "city_id") long cityId,
			@RequestParam (value = "search_date") String searchDate) {
		Session session = entityManager.unwrap(Session.class);

		String hql = "select theater FROM MovieShow S WHERE S.movie.id = :movie_id " +
				"						 and S.theater.address.city.id = :city_id" +
				"						 and S.date = :search_date";
		Query query = session.createQuery(hql);
		query.setParameter("movie_id",movieId);
		query.setParameter("city_id",cityId);

		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(searchDate);
			query.setParameter("search_date",date);
		} catch (ParseException e) {
			//TODO: --Handel Exception Invalid Date and Return Proper Response
			e.printStackTrace();
		}

		List<Theater> list = query.list();
		List<TheaterModel> theaterModels = new ArrayList<>();
		list.forEach(x-> {
			TheaterModel theaterModel = new TheaterModel(x.getId(),x.getName(), x.getPinCode());

			Link link = WebMvcLinkBuilder.linkTo(
							WebMvcLinkBuilder.methodOn(TheaterController.class).getTheaterById(x.getId())
						).withSelfRel();
			theaterModel.add(link);
			theaterModels.add(theaterModel);
		});

		return theaterModels;
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
	@PutMapping
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
