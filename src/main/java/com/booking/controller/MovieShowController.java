package com.booking.controller;

import com.booking.entity.MovieShow;
import com.booking.entity.Seat;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.MovieShowRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;


@RestController
@RequestMapping("/api/movie-show")
public class MovieShowController {

	@Autowired
	private MovieShowRepository movieShowRepository;

	@Autowired
	private EntityManager entityManager;

	// get all seats for a movie Show
	@GetMapping("/{id}/seats")
	public List<Seat> getSeatByMovieShow(
			@PathVariable ("id") long movieShowId
	) {
		MovieShow movieShowById = getMovieShowById(movieShowId);
		return movieShowById.getSeats();
	}

	//TODO: pagination
	@Hidden
	@GetMapping
	public List<MovieShow> getAllMovieShow() {
		return this.movieShowRepository.findAll();
	}

	@GetMapping("/{id}")
	public MovieShow getMovieShowById(@PathVariable (value = "id") long movieShowId) {
		return this.movieShowRepository.findById(movieShowId)
				.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + movieShowId));
	}

	@PostMapping
	public MovieShow createMovieShow(@RequestBody MovieShow movieShow) {
		return this.movieShowRepository.save(movieShow);
	}

	//--TODO: PAGINATION TO BE DONE HERE
	@Hidden
	@PutMapping("/{id}")
	public MovieShow updateMovieShow(@RequestBody MovieShow movieShow, @PathVariable ("id") long movieShowId) {
		MovieShow existingMovieShow = this.movieShowRepository.findById(movieShowId)
			.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + movieShowId));
		existingMovieShow.setName(movieShow.getName());
		existingMovieShow.setDate(movieShow.getDate());
		existingMovieShow.setStartTime(movieShow.getStartTime());
		existingMovieShow.setEndTime(movieShow.getEndTime());
		existingMovieShow.setMovie(movieShow.getMovie());
		existingMovieShow.setTheater(movieShow.getTheater());
		existingMovieShow.setScreen(movieShow.getScreen());
		existingMovieShow.setSeats(movieShow.getSeats());
		return this.movieShowRepository.save(existingMovieShow);
	}

	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<MovieShow> deleteMovieShow(@PathVariable ("id") long movieShowId){
		 MovieShow existingMovieShow = this.movieShowRepository.findById(movieShowId)
					.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + movieShowId));
		 this.movieShowRepository.delete(existingMovieShow);
		 return ResponseEntity.ok().build();
	}
}
