package ru.yandex.practicum.fillmorate.exception.film;

public class FilmNotFound extends RuntimeException {
    public FilmNotFound() {
    }

    public FilmNotFound(String message) {
        super(message);
    }
}
