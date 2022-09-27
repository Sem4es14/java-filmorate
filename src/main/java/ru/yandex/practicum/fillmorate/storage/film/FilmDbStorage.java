package ru.yandex.practicum.fillmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.exception.film.GenreNotFound;
import ru.yandex.practicum.fillmorate.exception.film.MpaNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        String sqlQueryFilm = "INSERT INTO films(name, description, release, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        if (film.getGenres() == null) {
            film.setGenres(new HashSet<>());
        }

        return addGenres(film);
    }

    @Override
    public Film update(Film film) {
        String updateFilmQuery = "UPDATE films SET " +
                "name = ?, description = ?, release = ?,  duration = ?, mpa_id = ? " +
                "WHERE id = ?";
        int status = jdbcTemplate.update(updateFilmQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        String deleteGenresQuery = "DELETE FROM genres_films WHERE film_id = ?";
        jdbcTemplate.update(deleteGenresQuery, film.getId());
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        if (film.getGenres() == null) {
            film.setGenres(new HashSet<>());
        }

        if (status == 1) return addGenres(film);
        throw new FilmNotFound("Film with id " + film.getId() + " not found");
    }

    public Film addGenres(Film film) {
        String sqlQueryGenres = "INSERT INTO genres_films(film_id, genre_id) " +
                "VALUES (?, ?) ";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQueryGenres,
                    film.getId(),
                    genre.getId()
            );
        }

        return getById(film.getId());
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM films;";

        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public Long deleteFilm(Long id) {
        return null;
    }

    @Override
    public Film getById(Long id) {
        String getFilmQuery = "SELECT * FROM films WHERE id = ?;";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(getFilmQuery, this::mapRowToFilm, id);
            film.setGenres(getGenres(id));
        } catch (EmptyResultDataAccessException e) {
            throw  new FilmNotFound("Film with id " + id + " not found");
        }

        return film;
    }

    @Override
    public List<Mpa> getMpas() {
        String getMpasQuery = "SELECT * FROM mpa";

        return jdbcTemplate.query(getMpasQuery, this::mapRowToMpa);
    }
    @Override
    public Mpa getMpaById(Long id) {
        String getMpaByIdQuery = "SELECT * FROM mpa WHERE id  = ?";

        try {
            return jdbcTemplate.queryForObject(getMpaByIdQuery, this::mapRowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFound("MPA with id: " + id + " is not found.");
        }

    }

    @Override
    public List<Genre> getAllGenres() {
        String getGenresQuery = "SELECT * FROM genres";

        return jdbcTemplate.query(getGenresQuery, this::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(Long id) {
        String getGenreByIdQuery = "SELECT * FROM genres WHERE id  = ?";

        try {
            return jdbcTemplate.queryForObject(getGenreByIdQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
        throw new GenreNotFound("Genre with id: " + id + " is not found.");
    }
    }

    private List<Long> getLikes (Long id) {
        String getLikesQuery = "SELECT l.user_id FROM likes AS l " +
                "JOIN films AS f ON l.film_id = f.id " +
                "WHERE l.user_id = ? ORDER BY l.user_id ";

        return jdbcTemplate.query(getLikesQuery, this::mapRowToUserLike, id);
    }

    private Set<Genre> getGenres (Long id) {
        String getGenreQuery = "SELECT * FROM genres_films AS gf " +
                "JOIN genres AS g ON gf.genre_id = g.ID " +
                "WHERE gf.film_id = ? ORDER BY g.id ";

        return new HashSet<>(jdbcTemplate.query(getGenreQuery, this::mapRowToGenre, id));
    }

    private Long mapRowToUserLike (ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong("user_id");
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        String sql = "SELECT * FROM mpa AS m " +
                "JOIN films AS f ON m.id = f.mpa_id " +
                "WHERE f.id = ?";

        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release").toLocalDate())
                .duration(resultSet.getLong("duration"))
                .mpa(jdbcTemplate.queryForObject(sql, this::mapRowToMpa, resultSet.getLong("id")))
                .genres(getGenres(resultSet.getLong("id")))
                .likes(new HashSet<>(getLikes(resultSet.getLong("id"))))
                .build();
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
