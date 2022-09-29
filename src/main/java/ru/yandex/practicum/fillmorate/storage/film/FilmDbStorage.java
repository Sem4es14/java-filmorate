package ru.yandex.practicum.fillmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.fillmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.fillmorate.storage.mpa.MpaDbStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Repository
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikeDbStorage likeStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage, MpaDbStorage mpaDbStorage, LikeDbStorage likeStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public Film save(Film film) {
        String sqlQueryFilm = "INSERT INTO films(name, description, release, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        mpaDbStorage.getMpaById(film.getMpa().getId());
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
        genreDbStorage.saveGenreByFilm(film);

        return getById(film.getId());
    }

    @Override
    public Film update(Film film) {
        String updateFilmQuery = "UPDATE films SET " +
                "name = ?, description = ?, release = ?,  duration = ?, mpa_id = ? " +
                "WHERE id = ?";
        mpaDbStorage.getMpaById(film.getMpa().getId());
        genreDbStorage.saveGenreByFilm(film);
        int status = jdbcTemplate.update(updateFilmQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        if (status == 1) {
            return getById(film.getId());
        }
        throw new FilmNotFound("Film with id " + film.getId() + " not found");
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM films;";

        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public Film getById(Long id) {
        String getFilmQuery = "SELECT * FROM films WHERE id = ?;";
        try {

            return jdbcTemplate.queryForObject(getFilmQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFound("Film with id " + id + " not found");
        }
    }

    @Override
    public List<Film> getPopular(int count) {
        String sql = "SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE, F.DURATION, F.MPA_ID " +
                "FROM FILMS AS F " +
                "LEFT JOIN LIKES L on F.ID = L.FILM_ID " +
                "GROUP BY F.ID " +
                "ORDER BY COUNT(DISTINCT (L.USER_ID)) DESC " +
                "LIMIT ? ";

        return jdbcTemplate.query(sql, this::mapRowToFilm, count);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {

        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release").toLocalDate())
                .duration(resultSet.getLong("duration"))
                .mpa(mpaDbStorage.getMpaById(resultSet.getLong("mpa_id")))
                .genres(genreDbStorage.getGenreByFilmId(resultSet.getLong("id")))
                .likes(new HashSet<>(likeStorage.getLikes(resultSet.getLong("id"))))
                .build();
    }
}
