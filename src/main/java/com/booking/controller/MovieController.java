package com.booking.controller;

import com.booking.entity.Movie;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movie")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping
	public List<Movie> getAllMovies() {
		return this.movieRepository.findAll();
	}

	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable (value = "id") long movieId) {
		return this.movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
	}

	@PostMapping
	public Movie createMovie(@RequestBody Movie movie) {
		return this.movieRepository.save(movie);
	}
	
	@PutMapping("/{id}")
	public Movie updateMovie(@RequestBody Movie movie, @PathVariable ("id") long movieId) {
		 Movie existingMovie = this.movieRepository.findById(movieId)
			.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
		 existingMovie.setName(movie.getName());
		 existingMovie.setImdbNumber(movie.getImdbNumber());
		 return this.movieRepository.save(existingMovie);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable ("id") long movieId){
		 Movie existingMovie = this.movieRepository.findById(movieId)
					.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
		 this.movieRepository.delete(existingMovie);
		 return ResponseEntity.ok().build();
	}
}
