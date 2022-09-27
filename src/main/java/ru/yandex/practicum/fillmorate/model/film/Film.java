package ru.yandex.practicum.fillmorate.model.film;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

import javax.persistence.Column;
import java.awt.geom.GeneralPath;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;
    private String name;
    private String description;
    @Column(name = "release")
    private LocalDate releaseDate;
    private Long duration;
    private Set<Genre> genres;
    private Mpa mpa;
    private Set<Long> likes;
}

