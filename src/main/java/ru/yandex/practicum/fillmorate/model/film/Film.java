package ru.yandex.practicum.fillmorate.model.film;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.fillmorate.model.user.User;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Set<User> likes;
}

