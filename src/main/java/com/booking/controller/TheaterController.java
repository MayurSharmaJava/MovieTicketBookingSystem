package com.booking.controller;

import com.booking.entity.MovieShow;
import com.booking.entity.Theater;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.pojo.MovieShowModel;
import com.booking.repository.pojo.TheaterModel;
import com.booking.repository.TheaterRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

	@GetMapping
	public List<MovieShowModel> search(
			@RequestParam ("movie_id") long movieId,
			@RequestParam (value = "city_id") long cityId,
			@RequestParam (value = "search_date") String searchDate,
			@RequestParam (value = "page") int page,
			@RequestParam (value = "size") int size) {
		Session session = entityManager.unwrap(Session.class);

		String hql = "FROM MovieShow S WHERE S.movie.id = :movie_id " +
				"						 and S.theater.address.city.id = :city_id" +
				"						 and S.date = :search_date";
		Query query = session.createQuery(hql);
		query.setFirstResult(page*size);
		query.setMaxResults(size);

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

		List<MovieShow> list = query.list();
		List<MovieShowModel> movieShowModels = new ArrayList<>();
		list.forEach(x-> {
			Theater theater = x.getTheater();
			TheaterModel theaterModel = new TheaterModel(theater.getId(),theater.getName(), theater.getPinCode());
			Link theaterLink = WebMvcLinkBuilder.linkTo(
							WebMvcLinkBuilder.methodOn(TheaterController.class).getTheaterById(theater.getId())
						).withRel("Theater Details");
			theaterModel.add(theaterLink);

			MovieShowModel movieShowModel = new MovieShowModel();
			movieShowModel.setTheater(theaterModel);

			movieShowModel.setId(x.getId());
			movieShowModel.setName(x.getName());
			movieShowModel.setMovie(x.getMovie());
			movieShowModel.setDate(x.getDate());
			movieShowModel.setStartTime(x.getStartTime());
			movieShowModel.setEndTime(x.getEndTime());

			Link seatLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovieShowController.class).getSeatByMovieShow(x.getId())).withRel("seat availability");

			movieShowModel.add(seatLink);

			movieShowModels.add(movieShowModel);
		});

		return movieShowModels;
	}

	@GetMapping("/{id}")
	public Theater getTheaterById(@PathVariable (value = "id") long theaterId) {
		return this.theaterRepository.findById(theaterId)
				.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + theaterId));
	}

	@PostMapping
	public Theater createTheater(@RequestBody Theater theater) {
		return this.theaterRepository.save(theater);
	}
	
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Theater> deleteTheater(@PathVariable ("id") long theaterId){
		 Theater existingTheater = this.theaterRepository.findById(theaterId)
					.orElseThrow(() -> new ResourceNotFoundException("Theater not found with id :" + theaterId));
		 this.theaterRepository.delete(existingTheater);
		 return ResponseEntity.ok().build();
	}
}
