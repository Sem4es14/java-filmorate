package ru.yandex.practicum.fillmorate.storage.film;

import ru.yandex.practicum.fillmorate.model.film.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film save(Film film);
    Film update(Film film);
    List<Film> getAll();
    String deleteFilm(Long id);

    Film getById(Long id);
}
