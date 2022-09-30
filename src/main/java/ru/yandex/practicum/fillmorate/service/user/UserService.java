package ru.yandex.practicum.fillmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fillmorate.mapper.user.UserMapper;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.fillmorate.requests.user.UserUpdateRequest;
import ru.yandex.practicum.fillmorate.storage.friend.FriendDbStorage;
import ru.yandex.practicum.fillmorate.storage.user.UserStorage;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendDbStorage friendStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendDbStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
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

        return friendStorage.addFriend(
                userStorage.getById(id),
                userStorage.getById(friendId)
        );
    }

    public User deleteFriend(Long id, Long friendId) {

        return friendStorage.deleteFriend(
                userStorage.getById(id),
                userStorage.getById(friendId)
        );
    }

    public Set<User> getFriends(Long id) {

        return friendStorage.getFriends(userStorage.getById(id));
    }

    public Set<User> getCommonFriends(Long id, Long otherId) {

        return friendStorage.getCommonFriends(
                userStorage.getById(id),
                userStorage.getById(otherId)
        );
    }
}
