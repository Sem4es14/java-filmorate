package ru.yandex.practicum.fillmorate.model.film;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

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
    private Set<Genre> genres;
    private Mpa mpa;
    private Set<Long> likes;
}

