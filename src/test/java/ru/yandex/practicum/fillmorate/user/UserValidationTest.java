package ru.yandex.practicum.fillmorate.user;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.fillmorate.controller.user.UserController;
import ru.yandex.practicum.fillmorate.model.UserRequest;
import ru.yandex.practicum.fillmorate.service.user.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserValidationTest {
    Gson gson = new Gson();

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createUserWithEmptyEmail() throws Exception {
        UserRequest user = getCorrectUser();
        user.setEmail(" ");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithEmailWithoutAtSign() throws Exception {
        UserRequest user = getCorrectUser();
        user.setEmail("user.yandex.ru");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithEmptyLogin() throws Exception {
        UserRequest user = getCorrectUser();
        user.setLogin("");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithEmailWithSpace() throws Exception {
        UserRequest user = getCorrectUser();
        user.setEmail("dfd dfdf");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void createUserWithBirthdayInFuture() throws Exception {
        UserRequest user = getCorrectUser();
        user.setBirthday("2025-10-10");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    private static UserRequest getCorrectUser() {
        return UserRequest.builder()
                .birthday("2020-10-10")
                .email("user@yandex.ru")
                .login("login")
                .name("name")
                .build();
    }
}
