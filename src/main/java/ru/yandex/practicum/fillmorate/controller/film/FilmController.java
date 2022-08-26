package ru.yandex.practicum.fillmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;
import ru.yandex.practicum.fillmorate.requests.film.FilmUpdateRequest;
import ru.yandex.practicum.fillmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
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

    @PutMapping("/{id}/like/{userId}")
    public String addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public String deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopular(@RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.of(Optional.of(filmService.getPopular(count)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(filmService.getById(id)));
    }
}
