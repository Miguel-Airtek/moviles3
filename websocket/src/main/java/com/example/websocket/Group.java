package com.example.websocket;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "groups")
@Data
public class Group {

    @Id
    private String id;
    private String name;
    private List<String> members;

    public Group() {
        this.id = UUID.randomUUID().toString();
    }

}
