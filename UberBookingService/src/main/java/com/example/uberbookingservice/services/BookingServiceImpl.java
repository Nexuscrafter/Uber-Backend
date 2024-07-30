package com.example.uberbookingservice.services;

import com.example.uberbookingservice.apis.LocationServiceApi;
import com.example.uberbookingservice.apis.UberSocketApi;
import com.example.uberbookingservice.dto.*;
import com.example.uberbookingservice.repositories.BookingRepository;
import com.example.uberbookingservice.repositories.DriverRepository;
import com.example.uberbookingservice.repositories.PassengerRepository;
import com.example.uberprojectentityservice.models.Booking;
import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Driver;
import com.example.uberprojectentityservice.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerRepository passengerRepository;

    private final BookingRepository bookingRepository;

    private final RestTemplate restTemplate;

    private final LocationServiceApi locationServiceApi;

    private final UberSocketApi uberSocketApi;

    private final DriverRepository driverRepository;

//    private static final String LOCATION_SERVICE = "http://localhost:7777";


    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi, DriverRepository driverRepository, UberSocketApi uberSocketApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.locationServiceApi = locationServiceApi;
        this.restTemplate = new RestTemplate();
        this.driverRepository = driverRepository;
        this.uberSocketApi = uberSocketApi;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        //make an api call to location service to fetch nearby drivers

        NearbyDriversRequestDto request = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request, bookingDetails.getPassengerId(), newBooking.getId());
//
//        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE+"/api/location/nearby/drivers", request,DriverLocationDto[].class);
//
//        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
//            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
//            driverLocations.forEach(driverLocationDto -> {
//                System.out.println(driverLocationDto.getDriverId() + " "+ "lat: " + driverLocationDto.getLatitude()+ "long: "+ driverLocationDto.getLongitude());
//            });
//        }
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId) {

        System.out.println(bookingRequestDto.getDriverId().get());
        Optional<Driver> driver = driverRepository.findById(bookingRequestDto.getDriverId().get());
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingStatus.SCHEDULED, driver.get());
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .status(booking.get().getBookingStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();

    }

    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto, Long passengerId, Long bookingId){
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);

        call.enqueue(new Callback<DriverLocationDto[]>() {

            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {

                if (response.isSuccessful() && response.body() != null) {
                  List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                  driverLocations.forEach(driverLocationDto -> {
                      System.out.println(driverLocationDto.getDriverId() + " "+ "lat: " + driverLocationDto.getLatitude()+ "long: "+ driverLocationDto.getLongitude());
                  });
                  try {
                      raiseRideRequestAsync(RideRequestDto.builder()
                                                .passengerId(passengerId)
                                                .bookingId(bookingId)
                                                .build());
                  } catch (Exception e){
                      throw new RuntimeException(e);
                  }
                }else{
                    System.out.println("Request failed" + response.message());
                }

            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                 throwable.printStackTrace();
            }
        });
    }

    private void raiseRideRequestAsync(RideRequestDto requestDto){
        Call<Boolean> call = uberSocketApi.raiseRideRequest(requestDto);

        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.message());
                if (response.isSuccessful() && response.body() != null) {

                    Boolean result = response.body();
                    System.out.println("Driver response is" + result.toString());

                }else{
                    System.out.println("Request for ride is failed" + response.message());
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

                throwable.printStackTrace();

            }
        });
    }
}
