package com.sharma.mayur.repository;


import com.sharma.mayur.entity.City;
import com.sharma.mayur.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

}
