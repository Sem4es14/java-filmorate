package ru.yandex.practicum.fillmorate.requests.film;

import lombok.Data;

@Data
public class FilmUpdateRequest extends FilmAddRequest {
    private Long id;
}

