package ru.yandex.practicum.fillmorate.storage.genre;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.film.GenreNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getGenreByFilm(Film film) {
        String getGenresQuery = "SELECT * FROM genres_films WHERE film_id = ?";

        return jdbcTemplate.query(getGenresQuery, this::mapRowToGenre, film.getId());
    }

    public Genre getGenreById(Long id) {
        String getGenreByIdQuery = "SELECT * FROM genres WHERE id  = ?";
        try {
            return jdbcTemplate.queryForObject(getGenreByIdQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFound("Genre with id: " + id + " is not found.");
        }
    }

    public List<Genre> getAllGenres() {
        String getGenresQuery = "SELECT * FROM genres";

        return jdbcTemplate.query(getGenresQuery, this::mapRowToGenre);
    }

    public Long saveGenreByFilm(Film film) {
        if (film.getGenres() == null) {
            return film.getId();
        }

        String deleteGenresQuery = "DELETE FROM genres_films WHERE film_id = ?";
        jdbcTemplate.update(deleteGenresQuery, film.getId());

        String saveGenresQuery = "INSERT INTO genres_films (film_id, genre_id) (SELECT id FROM GENRES WHERE";
        return null;
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
