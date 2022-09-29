package ru.yandex.practicum.fillmorate.friend;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.storage.friend.FriendDbStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendStorageTests {
    private User user;
    private final UserDbStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    @BeforeEach
    private void beforeEach() {
        user = User.builder()
                .birthday(LocalDate.EPOCH)
                .email("user@yandex.ru")
                .login("login")
                .name("name")
                .build();
    }

    @Test
    public void addAndGetFriend() {
        User user1 = userStorage.save(user);
        User user2 = userStorage.save(user);
        friendDbStorage.addFriend(user1, user2);
        assertEquals(friendDbStorage.getFriends(user1).size(), 1);
    }

    @Test
    public void getCommonFriends() {
        User user1 = userStorage.save(user);
        User user2 = userStorage.save(user);
        User user3 = userStorage.save(user);
        friendDbStorage.addFriend(user1, user2);
        friendDbStorage.addFriend(user1, user3);
        friendDbStorage.addFriend(user2, user3);
        assertEquals(friendDbStorage.getCommonFriends(user1, user2).size(), 1);
    }

    @Test
    public void addAndDeleteFriend() {
        User user1 = userStorage.save(user);
        User user2 = userStorage.save(user);
        friendDbStorage.addFriend(user1, user2);
        assertEquals(friendDbStorage.getFriends(user1).size(), 1);
        friendDbStorage.deleteFriend(user1, user2);
        assertEquals(friendDbStorage.getFriends(user1).size(), 0);
    }
}
