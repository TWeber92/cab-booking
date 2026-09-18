package com.infy.cabbooking.service;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.exception.CabBookingException;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;
import com.infy.cabbooking.validator.BookingValidator;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "cabBookingService")
@Transactional
public class CabBookingServiceImpl implements CabBookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private CabRepository cabRepository;

  @Override
  public List<BookingDTO> getDetailsByBookingType(String bookingType)
    throws CabBookingException {
    List<Booking> bookingsByType = bookingRepository.getDetailsByBookingType(
      bookingType
    );
    List<BookingDTO> bookingDTOs = new ArrayList<>();
    bookingsByType.forEach(booking -> {
      Cab cab = booking.getCab();
      bookingDTOs.add(
        new BookingDTO(
          booking.getBookingId(),
          booking.getCustomerName(),
          booking.getPhoneNo(),
          booking.getBookingType(),
          new CabDTO(
            cab.getCabNo(),
            cab.getModelName(),
            cab.getDriverPhoneNo(),
            cab.getAvailability()
          )
        )
      );
    });
    if (bookingDTOs.isEmpty()) throw new CabBookingException(
      "Service.NO_DETAILS_FOUND"
    );
    return bookingDTOs;
  }

  @Override
  public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException {
    new BookingValidator().validate(bookingDTO);
    Optional<Cab> optional = cabRepository.findById(
      bookingDTO.getCabDTO().getCabNo()
    );
    Cab cab = optional.orElseThrow(() ->
      new CabBookingException("Service.CAB_NOT_FOUND")
    );
    if (cab.getAvailability().equals("No")) throw new CabBookingException(
      "Service.CAB_NOT_AVAILABLE"
    );
    cab.setAvailability("No");
    cabRepository.save(cab);
    Booking booking = new Booking();
    booking.setCustomerName(bookingDTO.getCustomerName());
    booking.setPhoneNo(bookingDTO.getPhoneNo());
    booking.setBookingType(bookingDTO.getBookingType());
    booking.setCab(cab);
    Booking savedBooking = bookingRepository.save(booking);
    return savedBooking.getBookingId();
  }
}
