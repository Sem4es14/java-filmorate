package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.requests.user.UserCreateRequest;
import ru.yandex.practicum.filmorate.requests.user.UserUpdateRequest;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long createUser(@Valid @RequestBody UserCreateRequest request) {

        return userService.createUser(request);
    }

    @PutMapping
    public Long updateUser(@Valid @RequestBody UserUpdateRequest request) {

        return userService.updateUser(request);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.of(Optional.of(userService.getAll()));
    }
}
