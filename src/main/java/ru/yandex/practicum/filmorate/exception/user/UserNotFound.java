package ru.yandex.practicum.filmorate.exception.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
    }

    public UserNotFound(String message) {
        super(message);
    }

}
