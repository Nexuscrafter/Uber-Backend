package com.example.clientsocketservice.controller;

import com.example.clientsocketservice.dto.ChatRequest;
import com.example.clientsocketservice.dto.ChatResponse;
import com.example.clientsocketservice.dto.TestRequest;
import com.example.clientsocketservice.dto.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

//@Controller
//public class TestController {
//
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    public TestController(SimpMessagingTemplate simpMessagingTemplate){
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }
//
//    @MessageMapping("/ping")
//    @SendTo("/topic/ping") //used in conjunction with message mapping only
//    public TestResponse pingCheck(TestRequest message){
//        System.out.println("Received message from client " + message.getData());
//        return TestResponse.builder().data("Received").build();
//    }
//
//
//    @Scheduled(fixedDelay = 2000)
//    public void sendPeriodicMessage() {
//        System.out.println("Executed periodic function");
//        simpMessagingTemplate.convertAndSend("/topic/scheduled", "Periodic message sent "+ System.currentTimeMillis());
//    }
//
//    //every request coming to "/chat/{room} will only be sent back to the same room
//    @MessageMapping("/chat/{room}")
//    @SendTo("/topic/message/{room}")
//    public ChatResponse chatMessage(@DestinationVariable String room, ChatRequest request){
//        return ChatResponse.builder()
//                .name(request.getName())
//                .message(request.getMessage())
//                .timeStamp("" + System.currentTimeMillis())
//                .build();
//    }
//
//    //1:1 private chat with specific user
//    @MessageMapping("/privateChat/{room}/{userId}")
//    @SendTo("/topic/privateMessage/{room}/{userId}")
//    public void privateChatMessage(@DestinationVariable String room, @DestinationVariable String userId, ChatRequest request){
//        ChatResponse response = ChatResponse.builder()
//                .name(request.getName())
//                .message(request.getMessage())
//                .timeStamp("" + System.currentTimeMillis())
//                .build();
//
//        simpMessagingTemplate.convertAndSendToUser(userId,"/queue/privateMessage/" + room, response);
//
//    }
//
//
//}
