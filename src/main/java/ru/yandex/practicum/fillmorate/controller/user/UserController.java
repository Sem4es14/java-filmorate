package ru.yandex.practicum.fillmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.user.User;
import ru.yandex.practicum.fillmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.fillmorate.requests.user.UserUpdateRequest;
import ru.yandex.practicum.fillmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        log.info("Request to create user: " + request);

        return ResponseEntity.of(Optional.of(userService.createUser(request)));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        log.info("Request to update user: " + request);

        return ResponseEntity.of(Optional.of(userService.updateUser(request)));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.of(Optional.of(userService.getAll()));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(userService.getFriends(id)));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Set<User>> getFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return ResponseEntity.of(Optional.of(userService.getCommonFriends(id, otherId)));
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(userService.getById(id)));
    }
}
