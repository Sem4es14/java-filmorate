package ru.yandex.practicum.fillmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.service.film.FilmService;

import javax.validation.Valid;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public ResponseEntity<Film> addFilm(@Valid @RequestBody FilmAddRequest request) {
        log.info("Request to add film: " + request);

        return ResponseEntity.of(Optional.of(filmService.addFilm(request)));
    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody FilmUpdateRequest request) {
        log.info("Request to update film: " + request);

        return ResponseEntity.of(Optional.of(filmService.updateFilm(request)));
    }

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getAllFilm() {
        return ResponseEntity.of(Optional.of(filmService.getAll()));
    }

    @PutMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> addLike(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.of(Optional.of(filmService.addLike(id, userId)));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.of(Optional.of(filmService.deleteLike(id, userId)));
    }

    @GetMapping("/films/popular")
    public ResponseEntity<List<Film>> getPopular(@RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.of(Optional.of(filmService.getPopular(count)));
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<Film> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(filmService.getById(id)));
    }

    @GetMapping("/mpa")
    public ResponseEntity<List<Mpa>> getMpas() {
        return ResponseEntity.of(Optional.of(filmService.getMpas()));
    }

    @GetMapping("/mpa/{id}")
    public ResponseEntity<Mpa> getMpaById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(filmService.getMpaById(id)));
    }
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.of(Optional.of(filmService.getGenres()));
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(filmService.getGenreById(id)));
    }
}
