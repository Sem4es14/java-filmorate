package ru.yandex.practicum.fillmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.mapper.film.FilmMapper;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.storage.film.FilmStorage;
import ru.yandex.practicum.fillmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeDbStorage likeStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, LikeDbStorage likeDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeDbStorage;
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

        return likeStorage.addLike(
                filmStorage.getById(filmId),
                userStorage.getById(userId)
        );
    }

    public Film deleteLike(Long filmId, Long userId) {
        return likeStorage.deleteLike(
                filmStorage.getById(filmId),
                userStorage.getById(userId)
        );
    }

    public List<Film> getPopular(int count) {

        return filmStorage.getPopular(count);
    }

    public Film getById(Long id) {

        return filmStorage.getById(id);
    }
}
