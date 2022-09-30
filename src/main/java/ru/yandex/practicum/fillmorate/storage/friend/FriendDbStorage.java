package ru.yandex.practicum.fillmorate.storage.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.fillmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class FriendDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User addFriend(User user, User friend) {
        String addFriendSqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(addFriendSqlQuery, user.getId(), friend.getId());

        return user;
    }

    public User deleteFriend(User user, User friend) {
        String deleteFriendSqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(deleteFriendSqlQuery, user.getId(), friend.getId());

        return user;
    }

    public Set<User> getFriends(User user) {
        String getFriendsQuery = "SELECT * FROM friends AS f " +
                "JOIN users AS u ON f.friend_id = u.id " +
                "WHERE f.user_id = ?";

        return new HashSet<>(jdbcTemplate.query(getFriendsQuery, this::mapRowToUser, user.getId()));
    }

    public Set<User> getCommonFriends(User user, User friend) {
        String getCommonFriendsQuery = "SELECT * FROM USERS " +
                "WHERE ID IN ( " +
                "SELECT FRIEND_ID FROM FRIENDS " +
                "WHERE USER_ID = ? AND FRIEND_ID IN " +
                "                      (SELECT FRIEND_ID " +
                "                       FROM FRIENDS " +
                "                       WHERE USER_ID = ?))";

        return new HashSet<>(jdbcTemplate.query(getCommonFriendsQuery,
                this::mapRowToUser,
                user.getId(),
                friend.getId()));
    }

    public Set<Long> getFriendsIds(Long id) {
        String getFriendsQuery = "SELECT * FROM friends " +
                "WHERE user_id = ?";

        return new HashSet<>(jdbcTemplate.query(getFriendsQuery, this::mapRowToFriendsIds, id));
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .friends(getFriendsIds(resultSet.getLong("id")))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    private Long mapRowToFriendsIds(ResultSet resultSet, int rowNum) throws SQLException {

        return resultSet.getLong("friend_id");
    }
}
