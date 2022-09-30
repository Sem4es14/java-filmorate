package ru.yandex.practicum.fillmorate.storage.user;

import ru.yandex.practicum.fillmorate.model.user.User;

import java.util.List;

public interface UserStorage {
    User save(User user);

    User update(User user);

    List<User> getAll();

    User getById(Long id);
}

