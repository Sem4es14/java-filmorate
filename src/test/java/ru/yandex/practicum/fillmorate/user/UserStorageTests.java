package ru.yandex.practicum.fillmorate.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.storage.user.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageTests {
    private User user;
    private final UserDbStorage userStorage;

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
    public void saveUser() {
        assertNotNull(userStorage.save(user).getId());
    }

    @Test
    public void updateUser() {
        Long id = userStorage.save(user).getId();
        user.setId(id);
        user.setName("anotherName");

        assertEquals(userStorage.update(user).getName(), "anotherName");
    }

    @Test
    public void getAllUser() {
        int countUser = userStorage.getAll().size();
        userStorage.save(user);
        userStorage.save(user);

        assertEquals(userStorage.getAll().size(), countUser + 2);
    }

    @Test
    public void getUserById() {
        Long id  = userStorage.save(user).getId();

        assertEquals(userStorage.getById(id).getId(), id);
    }
}
