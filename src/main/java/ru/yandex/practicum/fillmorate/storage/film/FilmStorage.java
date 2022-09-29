package ru.yandex.practicum.fillmorate.storage.film;

import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

import java.util.List;

public interface FilmStorage {
    Film save(Film film);
    Film update(Film film);
    List<Film> getAll();

    Film getById(Long id);

}
