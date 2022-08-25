package ru.yandex.practicum.fillmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.film.LikesComparator;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.storage.film.FilmStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(FilmAddRequest request) {
        Film film = Film.builder()
                .description(request.getDescription())
                .duration(request.getDuration())
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .likes(new HashSet<>() )
                .build();

        return filmStorage.save(film);
    }

    public Film updateFilm(FilmUpdateRequest request) {
        Film film = filmStorage.getById(request.getId());
                film.setDescription(request.getDescription());
                film.setDuration(request.getDuration());
                film.setName(request.getName());
                film.setReleaseDate(request.getReleaseDate());
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public String addLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().add(user);
        return "OK";
    }

    public String deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().remove(user);
        return "OK";
    }

    public List<Film> getPopular(int count) {
        Set<Film> films = new TreeSet<>(new LikesComparator());
        films.addAll(filmStorage.getAll());
        return films.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
