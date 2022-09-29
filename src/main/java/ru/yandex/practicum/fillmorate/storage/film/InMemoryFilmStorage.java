package ru.yandex.practicum.fillmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;

import java.util.*;

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
        film.setLikes(new HashSet<>());
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
    public List<Film> getPopular(int count) {
        return null;
    }


}


