package ru.yandex.practicum.fillmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fillmorate.model.User;
import ru.yandex.practicum.fillmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.fillmorate.requests.user.UserUpdateRequest;
import ru.yandex.practicum.fillmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
}
