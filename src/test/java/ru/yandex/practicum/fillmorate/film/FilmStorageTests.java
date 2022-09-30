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

        assertEquals(filmStorage.getById(id).getId(), film.getId());
    }

    @Test
    public void getAllFilms() {
        filmStorage.save(film);
        filmStorage.save(film);

        assertNotEquals(filmStorage.getAll().size(), 0);
    }
}
