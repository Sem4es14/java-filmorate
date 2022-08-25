package ru.yandex.practicum.fillmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilmRequest {
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}
