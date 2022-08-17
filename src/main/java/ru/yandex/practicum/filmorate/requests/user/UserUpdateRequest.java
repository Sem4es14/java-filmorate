package ru.yandex.practicum.filmorate.requests.user;

import lombok.Data;

@Data
public class UserUpdateRequest extends UserCreateRequest {
    private Long id;
}

