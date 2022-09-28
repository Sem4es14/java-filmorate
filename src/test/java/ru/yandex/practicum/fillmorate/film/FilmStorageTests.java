package ru.yandex.practicum.fillmorate.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageTests {
    private Film film;
    private final FilmDbStorage filmStorage;

    @BeforeEach
    public void beforeEach() {
        film =  Film.builder()
                .name("The Great Gatsby")
                .description("nice nice")
                .duration(125l)
                .releaseDate(LocalDate.EPOCH)
                .mpa(new Mpa(1L ,""))
                .build();
    }

    @Test
    public void saveFilm() {
        assertNotNull(filmStorage.save(film).getId());
    }

    @Test
    public void updateFilm() {
        film = filmStorage.save(film);
        film.setName("anotherName");
        assertEquals(filmStorage.update(film).getName(), "anotherName");
    }

    @Test
    public void getByIdFilm() {
        Long id = filmStorage.save(film).getId();
        film.setId(id);
        film.setMpa(filmStorage.getMpaById(film.getMpa().getId()));

        assertEquals(filmStorage.getById(id), film);
    }

    @Test
    public void getAllFilms() {
        filmStorage.save(film);
        filmStorage.save(film);

        assertNotEquals(filmStorage.getAll().size(), 0);
    }

    @Test
    public void getMpas() {
        assertEquals(filmStorage.getMpas().size(), 5);
        assertEquals(filmStorage.getMpas().get(0).getName(), "G");
    }

    @Test
    public void getMpasByid() {
        assertEquals(filmStorage.getMpaById(1L).getName(), "G");
    }

    @Test
    public void getGenres() {
        assertEquals(filmStorage.getAllGenres().size(), 6);
        assertEquals(filmStorage.getAllGenres().get(0).getName(), "Комедия");
    }

    @Test
    public void getGenreByid() {
        assertEquals(filmStorage.getGenreById(1L).getName(), "Комедия");
    }

}
