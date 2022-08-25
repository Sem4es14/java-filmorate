package ru.yandex.practicum.fillmorate.user;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserValidationTest {
    Gson gson = new Gson();
    UserRequest user;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    private void beforeEach() {
        user = UserRequest.builder()
                .birthday("2020-10-10")
                .email("user@yandex.ru")
                .login("login")
                .name("name")
                .build();
    }

    @Test
    public void createUserWithEmptyEmail() throws Exception {
        user.setEmail("");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email cannot be empty")));
    }

    @Test
    public void createUserWithEmailWithoutAtSign() throws Exception {
        user.setEmail("user.yandex.ru");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Unsupported email type")));
    }

    @Test
    public void createUserWithEmptyLogin() throws Exception {
        user.setLogin("");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Login cannot be empty")));
    }

    @Test
    public void createUserWithEmailWithSpace() throws Exception {
        user.setEmail("invalid email");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Unsupported email type")));

    }

    @Test
    public void createUserWithBirthdayInFuture() throws Exception {
        user.setBirthday("2025-10-10");
        mockMvc.perform(post("/users")
                        .content(gson.toJson(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Birthday cannot be in future")));

    }
}
