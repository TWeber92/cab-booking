package com.infy.cabbooking;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;
import com.infy.cabbooking.service.CabBookingServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CabBookingApplicationTests {

  @Mock
  private CabRepository cabRepository;

  @Mock
  private BookingRepository bookingRepository;

  @InjectMocks
  private CabBookingServiceImpl cabBookingService;

  @Test
  public void bookCabInvalidCabNoTest() throws Exception {
    Cab cab = new Cab();
    cab.setAvailability("No");
    CabDTO cabDTO = new CabDTO();
    cabDTO.setAvailability(cab.getAvailability());
    BookingDTO bookingDTO = new BookingDTO();
    bookingDTO.setPhoneNo(7634583927L);
    bookingDTO.setCabDTO(cabDTO);
    Mockito.when(
      cabRepository.findById(bookingDTO.getCabDTO().getCabNo())
    ).thenReturn(Optional.of(cab));
    Exception e = Assertions.assertThrows(Exception.class, () ->
      cabBookingService.bookCab(bookingDTO)
    );
    Assertions.assertEquals("Service.CAB_NOT_AVAILABLE", e.getMessage());
  }

  @Test
  public void getDetailsByBookingTypeNoDetailsFound() throws Exception {
    String bookingType = "Mock";
    Exception e = Assertions.assertThrows(Exception.class, () ->
      cabBookingService.getDetailsByBookingType(bookingType)
    );
    Assertions.assertEquals("Service.NO_DETAILS_FOUND", e.getMessage());
  }
}
