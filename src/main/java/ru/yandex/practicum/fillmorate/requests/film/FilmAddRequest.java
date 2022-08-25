package ru.yandex.practicum.fillmorate.requests.film;

import lombok.Data;
import ru.yandex.practicum.fillmorate.requests.validation.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class FilmAddRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Size(max = 200, message = "Description cannot be more than 200 characters")
    private String description;
    @AfterDate(message = "Date cannot be before 1895.12.28")
    private LocalDate releaseDate;
    @Positive(message = "Duration cannot be negative")
    private Long duration;
}

