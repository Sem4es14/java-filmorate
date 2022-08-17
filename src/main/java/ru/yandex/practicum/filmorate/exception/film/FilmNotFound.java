package ru.yandex.practicum.filmorate.exception.film;

public class FilmNotFound extends RuntimeException {
    public FilmNotFound() {
    }

    public FilmNotFound(String message) {
        super(message);
    }

}
