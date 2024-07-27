package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(String name, List<String> participants) {
        Room room = new Room();
        room.setName(name);
        room.setParticipants(participants);
        return roomRepository.save(room);
    }

    public Room getRoomByName(String name) {
        return roomRepository.findByName(name);
    }
}

