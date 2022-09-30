package ru.yandex.practicum.fillmorate.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.fillmorate.requests.validation.WithoutWhitespace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @Email(message = "Unsupported email type")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Login cannot be empty")
    @WithoutWhitespace(message = "Login cannot be with whitespace")
    private String login;
    private String name;
    @Past(message = "Birthday cannot be in future")
    private LocalDate birthday;
}

