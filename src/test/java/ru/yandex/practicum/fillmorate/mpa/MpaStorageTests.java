package ru.yandex.practicum.fillmorate.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.fillmorate.storage.mpa.MpaDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaStorageTests {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void getMpas() {
        assertEquals(mpaDbStorage.getMpas().size(), 5);
        assertEquals(mpaDbStorage.getMpas().get(0).getName(), "G");
    }

    @Test
    public void getMpasByid() {
        assertEquals(mpaDbStorage.getMpaById(1L).getName(), "G");
    }
}
