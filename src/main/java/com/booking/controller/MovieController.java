package com.booking.controller;

import com.booking.constant.CommonConstant;
import com.booking.entity.Movie;
import com.booking.exception.ResourceNotFoundException;
import com.booking.pojo.MovieModel;
import com.booking.repository.MovieRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
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
	public MovieModel getMovieById(@PathVariable (value = "id") long movieId) {
		Movie movie = this.movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.MOVIE_NOT_FOUND_WITH_ID + movieId));
		return getMovieModel(movie);
	}

	private MovieModel getMovieModel(Movie movie) {
		MovieModel movieModel = new MovieModel(movie.getId(), movie.getName(), movie.getImdbNumber());
		Link imdbLink = Link.of("https://www.imdb.com/title/"+ movie.getImdbNumber(), LinkRelation.of("Self"));
		movieModel.add(imdbLink);
		return movieModel;
	}

	@	PostMapping
	public Movie createMovie(@RequestBody Movie movie) {
		return this.movieRepository.save(movie);
	}

	@Hidden
	@PutMapping("/{id}")
	public Movie updateMovie(@RequestBody Movie movie, @PathVariable ("id") long movieId) {
		 Movie existingMovie = this.movieRepository.findById(movieId)
			.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.MOVIE_NOT_FOUND_WITH_ID + movieId));
		 existingMovie.setName(movie.getName());
		 existingMovie.setImdbNumber(movie.getImdbNumber());
		 return this.movieRepository.save(existingMovie);
	}

	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable ("id") long movieId){
		 Movie existingMovie = this.movieRepository.findById(movieId)
					.orElseThrow(() -> new ResourceNotFoundException(CommonConstant.MOVIE_NOT_FOUND_WITH_ID + movieId));
		 this.movieRepository.delete(existingMovie);
		 return ResponseEntity.ok().build();
	}
}
