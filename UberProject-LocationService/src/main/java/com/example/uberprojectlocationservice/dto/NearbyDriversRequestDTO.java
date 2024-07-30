package com.example.uberprojectlocationservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyDriversRequestDTO {

    Double latitude;

    Double longitude;

}
