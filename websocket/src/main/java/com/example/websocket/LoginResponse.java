package com.example.websocket;

public class LoginResponse {
        private String message;
        private UserDTO user;

        public LoginResponse(String message, UserDTO user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }
    }

    class UserDTO {
        private String username;

        public UserDTO(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }


