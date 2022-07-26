package com.sharma.mayur.controller;

import com.sharma.mayur.entity.MovieShow;
import com.sharma.mayur.entity.Theater;
import com.sharma.mayur.repository.TheaterRepository;
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

	@Autowired
	private EntityManager entityManager;

	// get Booking by id
	@GetMapping("/{movie_id}/{city_id}/{search_date}")
	public List<MovieShow> getShowByMovie(
			@PathVariable (value = "movie_id") long movie_id,
			@PathVariable (value = "city_id") long city_id,
			@PathVariable (value = "search_date") String search_date) {
		Session session = entityManager.unwrap(Session.class);

		String hql = "FROM MovieShow S WHERE S.movie.id = :movie_id " +
				"						 and S.theater.address.city.id = :city_id" +
				"						 and S.date = :search_date";
		Query query = session.createQuery(hql);
		query.setParameter("movie_id",movie_id);
		query.setParameter("city_id",city_id);

		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(search_date);
			query.setParameter("search_date",date);
		} catch (ParseException e) {
			//--Handel Exception Invalid Date and Return Proper Response
			e.printStackTrace();
		}
		List<MovieShow> results = query.list();

		return results;
	}


}
