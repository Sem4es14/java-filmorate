package ru.yandex.practicum.fillmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody FilmAddRequest request) {
        log.info("Request to add film: " + request);

        return ResponseEntity.of(Optional.of(filmService.addFilm(request)));
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody FilmUpdateRequest request) {
        log.info("Request to update film: " + request);

        return ResponseEntity.of(Optional.of(filmService.updateFilm(request)));
    }


    @GetMapping
    public ResponseEntity<List<Film>> getAllFilm() {
        return ResponseEntity.of(Optional.of(filmService.getAll()));
    }
}
