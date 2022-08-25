package ru.yandex.practicum.fillmorate.service.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;

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

    public Film addFilm(FilmAddRequest request) {
        Film film = Film.builder()
                .id(id++)
                .description(request.getDescription())
                .duration(request.getDuration())
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .build();
        films.put(film.getId(), film);

        return film;
    }

    public Film updateFilm(FilmUpdateRequest request) {
        if (!films.containsKey(request.getId())) {
            throw new FilmNotFound("Film with id: " + request.getId() + " is not found");
        }
        Film film = Film.builder()
                .id(request.getId())
                .description(request.getDescription())
                .duration(request.getDuration())
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .build();
        films.put(film.getId(), film);

        return film;
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


