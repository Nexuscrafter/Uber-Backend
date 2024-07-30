package com.example.uberbookingservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long passengerId;

    private List<Long> driverIds;

    private Long bookingId;
}
