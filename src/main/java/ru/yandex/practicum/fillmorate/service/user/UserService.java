package ru.yandex.practicum.fillmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.mapper.user.UserMapper;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.fillmorate.requests.user.UserUpdateRequest;
import ru.yandex.practicum.fillmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(UserCreateRequest request) {
        User user = UserMapper.INSTANCE.requestToUser(request);
        return userStorage.save(user);
    }

    public User updateUser(UserUpdateRequest request) {
        User user = userStorage.getById(request.getId());
        user.setName(request.getName().isEmpty() ? request.getLogin() : request.getName());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());

        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public User addFriend(Long id, Long friendId) {
        User friend = userStorage.getById(friendId);
        User user = userStorage.getById(id);
        user.getFriends().add(friendId);

        return userStorage.update(user);
    }

    public User deleteFriend(Long id, Long friendId) {
        User friend = userStorage.getById(friendId);
        User user = userStorage.getById(id);
        user.getFriends().remove(friendId);

        return userStorage.update(user);
    }

    public Set<User> getFriends(Long id) {

        return userStorage.getById(id).getFriends().stream()
                .map(friendId -> userStorage.getById(friendId))
                .collect(Collectors.toSet());
    }

    public Set<User> getCommonFriends(Long id, Long otherId) {

        return userStorage.getById(id).getFriends().stream()
                .filter(userId -> userStorage.getById(otherId).getFriends().contains(userId))
                .map(friendId -> userStorage.getById(friendId))
                .collect(Collectors.toSet());
    }
}
