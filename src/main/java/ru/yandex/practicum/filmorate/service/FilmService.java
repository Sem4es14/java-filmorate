package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.filmorate.requests.film.FilmUpdateRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {
    private final Map<Long, Film> films;
    private Long id;

    public FilmService() {
        films = new HashMap<>();
        id = 1L;
    }

    public Long addFilm(FilmAddRequest request) {
        Film film = Film.builder()
                .id(id++)
                .description(request.getDescription())
                .duration(Duration.ofMinutes(request.getDuration()))
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .build();
        films.put(film.getId(), film);

        return film.getId();
    }

    public Long updateFilm(FilmUpdateRequest request) {
        Film film = Film.builder()
                .id(request.getId())
                .description(request.getDescription())
                .duration(Duration.ofMinutes(request.getDuration()))
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .build();
        films.put(film.getId(), film);

        return film.getId();
    }

    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    public String deleteFilm(Long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFound("Film with id: " + id + "is not found");
        }
        films.remove(id);

        return "SUCCESS";
    }
}


