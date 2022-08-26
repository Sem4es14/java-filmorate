package ru.yandex.practicum.fillmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.fillmorate.exception.user.UserNotFound;
import ru.yandex.practicum.fillmorate.model.user.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users;
    private Long id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 1L;
    }

    @Override
    public User save(User user) {
        user.setId(id++);
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public String delete(Long id) {
        users.remove(id);

        return "OK";
    }

    @Override
    public User getById(Long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFound("User with id: " + id + " is not found");
        }

        return users.get(id);
    }
}

