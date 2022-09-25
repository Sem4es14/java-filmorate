package ru.yandex.practicum.fillmorate.storage.film;

import ru.yandex.practicum.fillmorate.model.film.Film;

import java.util.List;

public interface FilmStorage {
    Film save(Film film);
    Film update(Film film);
    List<Film> getAll();
    Long deleteFilm(Long id);

    Film getById(Long id);
}
