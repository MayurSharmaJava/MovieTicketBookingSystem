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
	private MovieRepository MovieRepository;

	// get all Movies
	@GetMapping
	public List<Movie> getAllMovies() {
		return this.MovieRepository.findAll();
	}

	// get Movie by id
	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable (value = "id") long MovieId) {
		return this.MovieRepository.findById(MovieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + MovieId));
	}

	// create Movie
	@PostMapping
	public Movie createMovie(@RequestBody Movie Movie) {
		return this.MovieRepository.save(Movie);
	}
	
	// update Movie
	@PutMapping("/{id}")
	public Movie updateMovie(@RequestBody Movie Movie, @PathVariable ("id") long MovieId) {
		 Movie existingMovie = this.MovieRepository.findById(MovieId)
			.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + MovieId));
		 existingMovie.setName(Movie.getName());
		 existingMovie.setImdbNumber(Movie.getImdbNumber());
		 return this.MovieRepository.save(existingMovie);
	}
	
	// delete Movie by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable ("id") long MovieId){
		 Movie existingMovie = this.MovieRepository.findById(MovieId)
					.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id :" + MovieId));
		 this.MovieRepository.delete(existingMovie);
		 return ResponseEntity.ok().build();
	}
}
