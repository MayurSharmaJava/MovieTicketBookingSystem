package com.sharma.mayur.repository;


import com.sharma.mayur.entity.PreBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreBookingRepository extends JpaRepository<PreBooking, Long>{

}
