package com.sharma.mayur.repository;


import com.sharma.mayur.entity.Movie;
import com.sharma.mayur.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
