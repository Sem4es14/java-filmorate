package ru.yandex.practicum.fillmorate.requests.film;

import lombok.Data;
import ru.yandex.practicum.fillmorate.requests.validation.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class FilmAddRequest {
    @NotBlank
    private String name;
    @Size(max = 200, message = "error size")
    private String description;
    @AfterDate(message = "error date")
    private LocalDate releaseDate;
    @Positive
    private Long duration;
}

