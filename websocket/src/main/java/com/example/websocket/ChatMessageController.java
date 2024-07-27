package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/messages/{groupId}")
    public List<ChatMessage> getMessages(@PathVariable String groupId) {
        return chatMessageRepository.findByGroupId(groupId);
    }

    // New endpoint for individual chat messages
    @GetMapping("/individual/messages/{roomName}")
    public List<ChatMessage> getIndividualMessages(@PathVariable String roomName) {
        Room room = roomService.getRoomByName(roomName);
        return chatMessageRepository.findByRoom(room);
    }

    // New endpoint for sending individual chat messages
    @PostMapping("/individual/messages/{roomName}")
    public ChatMessage sendIndividualMessage(@PathVariable String roomName, @RequestBody ChatMessage message) {
        Room room = roomService.getRoomByName(roomName);
        message.setRoom(room);
        return chatMessageRepository.save(message);
    }
}
