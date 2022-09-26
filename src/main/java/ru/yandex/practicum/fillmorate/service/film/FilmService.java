package ru.yandex.practicum.fillmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.mapper.film.FilmMapper;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.film.LikesComparator;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.storage.film.FilmStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserStorage;

import java.util.List;
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
        Film film = FilmMapper.INSTANCE.requestToFilm(request);

        return filmStorage.save(film);
    }

    public Film updateFilm(FilmUpdateRequest request) {
        Film film = filmStorage.getById(request.getId());
                film.setDescription(request.getDescription());
                film.setDuration(request.getDuration());
                film.setName(request.getName());
                film.setReleaseDate(request.getReleaseDate());
                film.setGenres(request.getGenres());
                film.setMpa(request.getMpa());

        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film addLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().add(user.getId());

        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().remove(user.getId());

        return film;
    }

    public List<Film> getPopular(int count) {
        List<Film> films = filmStorage.getAll();
        films.sort(new LikesComparator());

        return films.stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getById(Long id) {

        return filmStorage.getById(id);
    }

    public List<Mpa> getMpas() {

        return filmStorage.getMpas();
    }

    public Mpa getMpaById(Long id) {

        return filmStorage.getMpaById(id);
    }

    public List<Genre> getGenres() {

        return filmStorage.getAllGenres();
    }

    public Genre getGenreById(Long id) {

        return filmStorage.getGenreById(id);
    }
}
