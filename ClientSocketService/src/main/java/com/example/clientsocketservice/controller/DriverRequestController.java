package com.example.clientsocketservice.controller;

import com.example.clientsocketservice.Producers.KafkaProducerService;
import com.example.clientsocketservice.dto.RideRequestDto;
import com.example.clientsocketservice.dto.RideResponseDto;
import com.example.clientsocketservice.dto.UpdateBookingRequestDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/api/socket")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RestTemplate restTemplate;

    private final KafkaProducerService kafkaProducerService;

    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate, KafkaProducerService kafkaProducerService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = new RestTemplate();
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping
    public Boolean help(){

        kafkaProducerService.publishMessage("sample-topic", "Hello");
        return true;

    }

    @PostMapping("/newride")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto requestDto){
        System.out.println("Request for ride received");
        sendDriversNewRideRequest(requestDto);
        System.out.println("Req Completed");
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    public void sendDriversNewRideRequest(RideRequestDto requestDto) {
        System.out.println("Executed periodic function");
        // Ideally the request should only go to nearby drivers, but for simplicity we send it to everyone
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", requestDto );
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto){
        System.out.println(rideResponseDto.getResponse() + " " + userId);
        UpdateBookingRequestDto requestDto = UpdateBookingRequestDto.builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .status("SCHEDULED")
                .build();
        ResponseEntity<UpdateBookingRequestDto> result = this.restTemplate.postForEntity("http://localhost:8001/api/v1/booking/" + rideResponseDto.bookingId, requestDto, UpdateBookingRequestDto.class);
        System.out.println(result.getStatusCode());
    }




}
