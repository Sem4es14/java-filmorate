package ru.yandex.practicum.fillmorate.controller.genre;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.service.genre.GenreService;

import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.of(Optional.of(genreService.getGenres()));
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(genreService.getGenreById(id)));
    }
}
