package ru.yandex.practicum.fillmorate.model.mpa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mpa {
    private Long id;
    private String name;
}
