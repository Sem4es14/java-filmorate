package ru.yandex.practicum.fillmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;
    private Long id;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        id = 1L;
    }

    @Override
    public Film save(Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(Long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFound("Film with id: " + id + " is not found");
        }
        return films.get(id);
    }

    @Override
    public String deleteFilm(Long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFound("Film with id: " + id + "is not found");
        }
        films.remove(id);

        return "OK";
    }
}


