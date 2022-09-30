package ru.yandex.practicum.fillmorate.storage.mpa;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.film.MpaNotFound;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mpa> getMpas() {
        String getMpasQuery = "SELECT * FROM mpa";

        return jdbcTemplate.query(getMpasQuery, this::mapRowToMpa);
    }

    public Mpa getMpaById(Long id) {
        String getMpaByIdQuery = "SELECT * FROM mpa WHERE id  = ?";
        try {
            return jdbcTemplate.queryForObject(getMpaByIdQuery, this::mapRowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFound("MPA with id: " + id + " is not found.");
        }
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {

        return Mpa.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
