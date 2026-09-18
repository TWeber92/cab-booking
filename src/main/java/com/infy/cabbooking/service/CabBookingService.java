package com.infy.cabbooking.service;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.exception.CabBookingException;
import java.util.List;

public interface CabBookingService {
  public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException;
  public List<BookingDTO> getDetailsByBookingType(String customerName)
    throws CabBookingException;
}
