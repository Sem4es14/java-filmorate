package ru.yandex.practicum.fillmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String email;
    private String login;
    private String name;
    private String birthday;
}

