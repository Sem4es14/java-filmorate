package ru.yandex.practicum.fillmorate.storage.genre;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.film.GenreNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<Genre> getGenreByFilmId(Long filmId) {
        String getGenresQuery = "SELECT * FROM genres AS g" +
                " JOIN genres_films AS gf ON gf.genre_id = g.id" +
                " WHERE film_id = ? " +
                "ORDER BY id ASC";

        return new HashSet<>(jdbcTemplate.query(getGenresQuery, this::mapRowToGenre, filmId));
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

    public int saveGenreByFilm(Film film) {
        if (film.getGenres() == null) {

            return 0;
        }

        String deleteGenresQuery = "DELETE FROM genres_films WHERE film_id = ?";
        jdbcTemplate.update(deleteGenresQuery, film.getId());
        String values = String.join(",", Collections.nCopies(film.getGenres().size(), "?"));
        String saveGenresQuery = "INSERT INTO genres_films (film_id, genre_id) " +
                "(SELECT f.id, g.id " +
                "FROM films AS f " +
                "JOIN genres AS g ON f.id = ? " +
                "WHERE g.id IN (" + values + "))";
        List<Long> ids = new ArrayList<>();
        ids.add(film.getId());
        ids.addAll(film.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList()));

        return jdbcTemplate.update(saveGenresQuery, ids.toArray());
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
