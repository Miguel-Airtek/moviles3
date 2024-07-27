package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void clearOldMoods() {
        LocalDateTime now = LocalDateTime.now();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getMoodTimestamp() != null && user.getMoodTimestamp().isBefore(now.minusHours(24))) {
                user.setMood(null);
                user.setMoodTimestamp(null);
                userRepository.save(user);
            }
        }
    }

}
