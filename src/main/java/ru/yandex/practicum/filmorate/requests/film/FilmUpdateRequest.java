package ru.yandex.practicum.filmorate.requests.film;

import lombok.Data;

@Data
public class FilmUpdateRequest extends FilmAddRequest {
    private Long id;
}

