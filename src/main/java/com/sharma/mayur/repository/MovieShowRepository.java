package com.sharma.mayur.repository;


import com.sharma.mayur.entity.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow, Long>{

}