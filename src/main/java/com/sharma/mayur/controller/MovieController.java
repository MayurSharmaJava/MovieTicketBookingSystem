package com.sharma.mayur.controller;

import com.sharma.mayur.entity.Movie;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/Movie")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	// get all Movies
	@GetMapping
	public List<Movie> getAllMovies() {
		return this.movieRepository.findAll();
	}

	// get Movie by id
	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable (value = "id") long movieId) {
		return this.movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
	}

	// create Movie
	@PostMapping
	public Movie createMovie(@RequestBody Movie movie) {
		return this.movieRepository.save(movie);
	}
	
	// update Movie
	@PutMapping("/{id}")
	public Movie updateMovie(@RequestBody Movie movie, @PathVariable ("id") long movieId) {
		 Movie existingMovie = this.movieRepository.findById(movieId)
			.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
		 existingMovie.setName(movie.getName());
		 existingMovie.setImdbNumber(movie.getImdbNumber());
		 return this.movieRepository.save(existingMovie);
	}
	
	// delete Movie by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable ("id") long movieId){
		 Movie existingMovie = this.movieRepository.findById(movieId)
					.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + movieId));
		 this.movieRepository.delete(existingMovie);
		 return ResponseEntity.ok().build();
	}
}
