package ru.yandex.practicum.fillmorate.controller.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.fillmorate.model.mpa.Mpa;
import ru.yandex.practicum.fillmorate.service.mpa.MpaService;

import java.util.List;
import java.util.Optional;

@RestController
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public ResponseEntity<List<Mpa>> getMpas() {
        return ResponseEntity.of(Optional.of(mpaService.getMpas()));
    }

    @GetMapping("/mpa/{id}")
    public ResponseEntity<Mpa> getMpaById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(mpaService.getMpaById(id)));
    }
}
