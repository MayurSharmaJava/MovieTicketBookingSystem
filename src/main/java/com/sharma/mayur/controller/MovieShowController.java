package com.sharma.mayur.controller;

import com.sharma.mayur.entity.MovieShow;
import com.sharma.mayur.exception.ResourceNotFoundException;
import com.sharma.mayur.repository.MovieShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/MovieShow")
public class MovieShowController {

	@Autowired
	private MovieShowRepository MovieShowRepository;

	// get all MovieShowShow
	@GetMapping
	public List<MovieShow> getAllMovieShowShow() {
		return this.MovieShowRepository.findAll();
	}

	// get MovieShow by id
	@GetMapping("/{id}")
	public MovieShow getMovieShowById(@PathVariable (value = "id") long MovieShowId) {
		return this.MovieShowRepository.findById(MovieShowId)
				.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + MovieShowId));
	}

	// create MovieShow
	@PostMapping
	public MovieShow createMovieShow(@RequestBody MovieShow MovieShow) {
		return this.MovieShowRepository.save(MovieShow);
	}
	
	// update MovieShow
	@PutMapping("/{id}")
	public MovieShow updateMovieShow(@RequestBody MovieShow MovieShow, @PathVariable ("id") long MovieShowId) {
		MovieShow existingMovieShow = this.MovieShowRepository.findById(MovieShowId)
			.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + MovieShowId));
		existingMovieShow.setName(MovieShow.getName());
		existingMovieShow.setDate(MovieShow.getDate());
		existingMovieShow.setStartTime(MovieShow.getStartTime());
		existingMovieShow.setEndTime(MovieShow.getEndTime());
		existingMovieShow.setMovie(MovieShow.getMovie());
		existingMovieShow.setTheater(MovieShow.getTheater());
		existingMovieShow.setScreen(MovieShow.getScreen());
		existingMovieShow.setSeats(MovieShow.getSeats());
		return this.MovieShowRepository.save(existingMovieShow);
	}
	
	// delete MovieShow by id
	@DeleteMapping("/{id}")
	public ResponseEntity<MovieShow> deleteMovieShow(@PathVariable ("id") long MovieShowId){
		 MovieShow existingMovieShow = this.MovieShowRepository.findById(MovieShowId)
					.orElseThrow(() -> new ResourceNotFoundException("MovieShow not found with id :" + MovieShowId));
		 this.MovieShowRepository.delete(existingMovieShow);
		 return ResponseEntity.ok().build();
	}
}
