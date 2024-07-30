package com.example.UberReviewService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" , "bookings"})
public class Passenger extends BaseModel {
    private String name;

    @OneToMany(mappedBy = "passenger") //to explicitly specify spring whether the booking is for a driver or a passenger as both passenger and driver has many to one relationship with booking
    private List<Booking> bookings = new ArrayList<>();
}
