package ru.yandex.practicum.fillmorate.exception.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
    }

    public UserNotFound(String message) {
        super(message);
    }
}
