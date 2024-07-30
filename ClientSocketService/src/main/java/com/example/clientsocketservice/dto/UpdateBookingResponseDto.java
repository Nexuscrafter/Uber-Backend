package com.example.clientsocketservice.dto;

import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Driver;

import java.util.Optional;

public class UpdateBookingResponseDto {

    private Long bookingId;

    private BookingStatus status;

    private Optional<Driver> driver;
}
