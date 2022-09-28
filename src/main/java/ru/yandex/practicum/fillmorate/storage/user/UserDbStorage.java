package ru.yandex.practicum.fillmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.exception.user.UserNotFound;
import ru.yandex.practicum.fillmorate.model.user.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Repository
@Primary
public class UserDbStorage implements UserStorage{
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sqlQuerySaveUser = "INSERT INTO users(email, login, name, birthday) " +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuerySaveUser, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));

            return stmt;

        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        user.setFriends(new HashSet<>());

        return addFriends(user);
    }

    @Override
    public User update(User user) {
        String sqlQueryupdateUser = "UPDATE users SET " +
                "email = ?, login = ?, name = ?,  birthday = ? " +
                "WHERE id = ?";

        int status = jdbcTemplate.update(sqlQueryupdateUser,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        String deleteGenresQuery = "DELETE FROM friends WHERE user_id = ?";
        jdbcTemplate.update(deleteGenresQuery, user.getId());

        if (status == 1) return addFriends(user);

        throw new UserNotFound("User with id " + user.getId() + " not found");
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users;";

        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public User getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw  new UserNotFound("User with id " + id + " not found");
        }

    }

    private User addFriends(User user) {
        String sqlQueryfriends = "INSERT INTO friends (user_id, friend_id) " +
                "VALUES (?, ?)";
        for (Long friendId : user.getFriends()) {
            jdbcTemplate.update(sqlQueryfriends,
                    user.getId(),
                    friendId
            );
        }

        return getById(user.getId());
    }

    private List<Long> getFriends(Long user_id){
        String getFriendsQuery = "SELECT * FROM friends WHERE user_id = ?";

        return jdbcTemplate.query(getFriendsQuery, this::mapRowToUserFriends, user_id);
    }
    private Long mapRowToUserFriends(ResultSet resultSet, int rowNum) throws SQLException {

        return resultSet.getLong("friend_id");
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .friends(new HashSet<>(getFriends(resultSet.getLong("id"))))
                .build();
    }
}
