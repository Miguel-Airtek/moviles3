package com.example.websocket;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class ChatMessage {

    private String id;
    private String groupId;
    private String sender;
    private String content;
    private String type;
    @DBRef
    private Room room; // For individual chats
    private long timestamp;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
