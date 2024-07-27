package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getGroups() == null) {
            user.setGroups(new ArrayList<>());
        }
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setGroups(userDetails.getGroups());
            updatedUser.setProfilePictureBase64(userDetails.getProfilePictureBase64()); // Add this line
            userRepository.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login successful", new UserDTO(user.get().getUsername())));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", null));
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupRepository.save(group);

        for (String username : group.getMembers()) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getGroups() == null) {
                    user.setGroups(new ArrayList<>());
                }
                user.getGroups().add(createdGroup.getId());
                userRepository.save(user);
            }
        }

        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable String groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            return ResponseEntity.ok(group.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // New endpoint to update profile picture in Base64
    @PutMapping("/{username}/profile-picture")
    public ResponseEntity<User> updateProfilePicture(@PathVariable String username, @RequestBody String profilePictureBase64) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setProfilePictureBase64(profilePictureBase64);
            userRepository.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Existing endpoint to update mood by username
    @PutMapping("/{username}/mood")
    public ResponseEntity<User> updateMoodByUsername(@PathVariable String username, @RequestBody String mood) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setMood(mood); // Set mood as a plain string
            updatedUser.setMoodTimestamp(LocalDateTime.now());
            userRepository.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }}

}
