package ru.yandex.practicum.fillmorate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Long addFilm(@Valid @RequestBody FilmAddRequest request) {

        return filmService.addFilm(request);
    }

    @PutMapping
    public Long updateFilm(@Valid @RequestBody FilmUpdateRequest request) {

        return filmService.updateFilm(request);
    }


    @GetMapping
    public ResponseEntity<List<Film>> getAllFilm() {
        return ResponseEntity.of(Optional.of(filmService.getAll()));
    }
}