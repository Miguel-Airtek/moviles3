package com.example.websocket;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;
    private String name; // Unique room identifier, e.g., "Miguel_Natalia"
    private List<String> participants; // List of participants in the room

    // Getters and setters
}
