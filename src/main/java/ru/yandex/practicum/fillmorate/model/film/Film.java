package ru.yandex.practicum.fillmorate.model.film;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
}

