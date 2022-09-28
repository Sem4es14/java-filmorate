package ru.yandex.practicum.fillmorate.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest extends UserCreateRequest {
    private Long id;
}

