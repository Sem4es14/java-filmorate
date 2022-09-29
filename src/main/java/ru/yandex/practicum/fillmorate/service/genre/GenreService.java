package ru.yandex.practicum.fillmorate.service.genre;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.model.genre.Genre;
import ru.yandex.practicum.fillmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Service
public class GenreService {
    private  final GenreDbStorage genreStorage;

    public GenreService(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenres() {

        return genreStorage.getAllGenres();
    }

    public Genre getGenreById(Long id) {

        return genreStorage.getGenreById(id);
    }
}
