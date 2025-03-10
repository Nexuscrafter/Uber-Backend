package com.example.uberbookingservice.dto;

import com.example.uberprojectentityservice.models.BookingStatus;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {

    private String status;

    private Optional<Long> driverId;
}
