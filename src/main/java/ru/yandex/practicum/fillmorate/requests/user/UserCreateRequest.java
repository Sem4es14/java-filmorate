package ru.yandex.practicum.fillmorate.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.fillmorate.requests.validation.WithoutWhitespace;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserCreateRequest {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @WithoutWhitespace
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}

