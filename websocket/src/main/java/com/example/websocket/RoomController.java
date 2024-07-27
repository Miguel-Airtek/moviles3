package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room.getName(), room.getParticipants());
    }

    @GetMapping("/{name}")
    public Room getRoom(@PathVariable String name) {
        return roomService.getRoomByName(name);
    }
}
