package ru.yandex.practicum.fillmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.exception.user.UserNotFound;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.fillmorate.requests.user.UserUpdateRequest;

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

    public User createUser(UserCreateRequest request) {
        User user = User.builder()
                .id(id++)
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .login(request.getLogin())
                .name(request.getName().isEmpty() ? request.getLogin() : request.getName())
                .build();
        users.put(user.getId(), user);

        return user;
    }

    public User updateUser(UserUpdateRequest request) {
        if (!users.containsKey(request.getId())) {
            throw new UserNotFound("User with id: " + request.getId() + " is not found");
        }
        User user = User.builder()
                .id(request.getId())
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .login(request.getLogin())
                .name(request.getName().isEmpty() ? request.getLogin() : request.getName())
                .build();
        if (user.getName().isEmpty()) {
            user.setName(request.getLogin());
        }
        users.put(user.getId(), user);

        return user;
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

