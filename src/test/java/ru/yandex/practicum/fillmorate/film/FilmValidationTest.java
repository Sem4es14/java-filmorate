package ru.yandex.practicum.fillmorate.film;

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
import ru.yandex.practicum.fillmorate.controller.film.FilmController;
import ru.yandex.practicum.fillmorate.model.FilmRequest;
import ru.yandex.practicum.fillmorate.service.film.FilmService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FilmController.class)
public class FilmValidationTest {
    Gson gson = new Gson();
    FilmRequest film;

    @MockBean
    FilmService filmService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    private void beforeEach() {
        film =  FilmRequest.builder()
                .name("The Great Gatsby")
                .description("nice nice")
                .duration(125)
                .releaseDate("2013-10-10")
                .build();
    }

    @Test
    public void createFilmWithEmptyName() throws Exception {
        film.setName("");
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Name cannot be empty")));
    }

    @Test
    public void  createFilmWithBigDescription() throws Exception {
        film.setDescription("a".repeat(201));
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Description cannot be more than 200 characters")));
    }

    @Test
    public void createFilmWithDateRelease() throws Exception {
        film.setReleaseDate("1500-10-10");
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Date cannot be before 1895.12.28")));
    }

    @Test
    public void createFilmWithNegativeDuration() throws Exception {
        film.setDuration(-100);
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Duration cannot be negative")));
    }
}
