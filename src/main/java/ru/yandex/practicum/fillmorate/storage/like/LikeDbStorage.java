package ru.yandex.practicum.fillmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LikeDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Film addLike(Film film, User user) {
        String addLikeSqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?) ";
        String testLikesQuery = "SELECT user_id FROM likes " +
                "WHERE user_id = ? AND film_id = ?";
        if (jdbcTemplate.query(testLikesQuery, this::mapRowToUserLike, user.getId(), film.getId()).isEmpty()) {
            jdbcTemplate.update(addLikeSqlQuery, film.getId(), user.getId());
        }

        return film;
    }

    public Film deleteLike(Film film, User user) {
        String deleteLikeSqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(deleteLikeSqlQuery, film.getId(), user.getId());

        return film;
    }

    public List<Long> getLikes (Long id) {
        String getLikesQuery = "SELECT user_id FROM likes " +
                "WHERE film_id = ? ";

        return jdbcTemplate.query(getLikesQuery, this::mapRowToUserLike, id);
    }

    private Long mapRowToUserLike (ResultSet resultSet, int rowNum) throws SQLException {

        return resultSet.getLong("user_id");
    }
}
