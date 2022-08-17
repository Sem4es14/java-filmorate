package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.filmorate.requests.user.UserUpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> users;
    private Long id;

    public UserService() {
        users = new HashMap<>();
        id = 1L;
    }

    public Long createUser(UserCreateRequest request) {
        User user = User.builder()
                .id(id++)
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .login(request.getLogin())
                .name(request.getName())
                .build();
        users.put(user.getId(), user);

        return user.getId();
    }

    public Long updateUser(UserUpdateRequest request) {
        User user = User.builder()
                .id(request.getId())
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .login(request.getLogin())
                .name(request.getName())
                .build();
        users.put(user.getId(), user);

        return user.getId();
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public String deleteUser(Long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFound("User with id: " + id + " is not found");
        }
        users.remove(id);

        return "SUCCESS";
    }
}

