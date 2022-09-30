package ru.yandex.practicum.fillmorate.service.mpa;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.storage.mpa.MpaDbStorage;

import java.util.List;

@Service
public class MpaService {
    private final MpaDbStorage mpaStorage;

    public MpaService(MpaDbStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getMpas() {

        return mpaStorage.getMpas();
    }

    public Mpa getMpaById(Long id) {

        return mpaStorage.getMpaById(id);
    }

}
