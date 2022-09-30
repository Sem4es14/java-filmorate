package ru.yandex.practicum.fillmorate.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.fillmorate.storage.genre.GenreDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreStorageTests {
    private final GenreDbStorage genreDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void getGenres() {
        assertEquals(genreDbStorage.getAllGenres().size(), 6);
        assertEquals(genreDbStorage.getAllGenres().get(0).getName(), "Комедия");
    }

    @Test
    public void getGenreByid() {
        assertEquals(genreDbStorage.getGenreById(1L).getName(), "Комедия");
    }

    @Test
    public void saveGenreDyFilm() {
        Film film = Film.builder()
                .name("The Great Gatsby")
                .description("nice nice")
                .duration(125l)
                .releaseDate(LocalDate.EPOCH)
                .mpa(new Mpa(1L, ""))
                .build();
        Set<Genre> genres = new HashSet<>();
        genres.add(genreDbStorage.getGenreById(1L));
        genres.add(genreDbStorage.getGenreById(3L));
        film.setGenres(genres);
        Long id = filmDbStorage.save(film).getId();
        assertEquals(filmDbStorage.getById(id).getGenres(), genres);
    }
}
