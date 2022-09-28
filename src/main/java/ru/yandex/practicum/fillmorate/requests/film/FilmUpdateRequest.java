package ru.yandex.practicum.fillmorate.requests.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmUpdateRequest extends FilmAddRequest {
    private Long id;
}

