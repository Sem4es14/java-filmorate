package ru.yandex.practicum.fillmorate.requests.user;

import lombok.Data;

@Data
public class UserUpdateRequest extends UserCreateRequest {
    private Long id;
}

