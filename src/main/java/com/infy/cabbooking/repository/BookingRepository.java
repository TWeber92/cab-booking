package com.infy.cabbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.infy.cabbooking.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer>{
	@Query("SELECT b FROM Booking b WHERE b.bookingType = :bookingType")
	List<Booking> getDetailsByBookingType(@Param(value="bookingType") String bookingType);


}
