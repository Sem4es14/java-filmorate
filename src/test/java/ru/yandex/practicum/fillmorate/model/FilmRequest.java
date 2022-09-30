package ru.yandex.practicum.fillmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;

/**
 Эти сущности нужны чтоб формировать запрос для удобной сериализации в тестах.
 */
@Data
@Builder
public class FilmRequest {
    private String name;
    private String description;
    private String releaseDate;
    private Mpa mpa;
    private int duration;
}
