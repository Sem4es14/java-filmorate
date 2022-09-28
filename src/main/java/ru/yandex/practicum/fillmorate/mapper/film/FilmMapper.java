package ru.yandex.practicum.fillmorate.mapper.film;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.fillmorate.model.film.Film;
import ru.yandex.practicum.fillmorate.requests.film.FilmAddRequest;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);
    Film  requestToFilm(FilmAddRequest request);
}
