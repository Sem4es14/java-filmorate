package ru.yandex.practicum.fillmorate.like;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.fillmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeStorageTests {
    private Film film;
    private User user;
    private final UserDbStorage userStorage;
    private final LikeDbStorage likeDbStorage;
    private final FilmDbStorage filmStorage;

    @BeforeEach
    private void beforeEach() {
        user = User.builder()
                .birthday(LocalDate.EPOCH)
                .email("user@yandex.ru")
                .login("login")
                .name("name")
                .build();

        film =  Film.builder()
                .name("The Great Gatsby")
                .description("nice nice")
                .duration(125l)
                .releaseDate(LocalDate.EPOCH)
                .mpa(new Mpa(1L ,""))
                .build();
    }

    @Test
    public void addAndGetLikes() {
        film = filmStorage.save(film);
        user = userStorage.save(user);
        likeDbStorage.addLike(film, user);
        assertEquals(likeDbStorage.getLikes(film.getId()).size(), 1);
    }

    @Test
    public void addAndGetLikesAndDelete() {
        film = filmStorage.save(film);
        user = userStorage.save(user);
        likeDbStorage.addLike(film, user);
        assertEquals(likeDbStorage.getLikes(film.getId()).size(), 1);
        likeDbStorage.deleteLike(film, user);
        assertEquals(likeDbStorage.getLikes(film.getId()).size(), 0);
    }
}
