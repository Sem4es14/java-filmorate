package ru.yandex.practicum.fillmorate.exception.film;

public class FilmNotFound extends RuntimeException {
    public FilmNotFound(String message) {
        super(message);
    }
}
