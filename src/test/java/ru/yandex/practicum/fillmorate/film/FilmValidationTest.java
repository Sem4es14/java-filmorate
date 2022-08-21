package ru.yandex.practicum.fillmorate.film;

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
import ru.yandex.practicum.fillmorate.controller.film.FilmController;
import ru.yandex.practicum.fillmorate.model.FilmRequest;
import ru.yandex.practicum.fillmorate.service.film.FilmService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FilmController.class)
public class FilmValidationTest {
    Gson gson = new Gson();

    @MockBean
    FilmService filmService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createFilmWithEmptyName() throws Exception {
        FilmRequest film = getCorrectFilm();
        film.setName("");
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void  createFilmWithBigDescription() throws Exception {
        FilmRequest film = getCorrectFilm();
        film.setDescription("odfod                           dofidjof                  odfodf          dnfodnf" +
                "oidjfodoooooooooojodjfodjofffffffffffffffffffffffffffdojsappppppppppppppppppppppppfoj[odjaf" +
                "aidjdjdjdjdjdjdjdjdjdjdjdjdjdjdjdjdjdjdjdjzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzbbzbz");
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createFilmWithDateRelease() throws Exception {
        FilmRequest film = getCorrectFilm();
        film.setReleaseDate("1500-10-10");
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createFilmWithNegativeDuration() throws Exception {
        FilmRequest film = getCorrectFilm();
        film.setDuration(-100);
        mockMvc.perform(post("/films")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    private static FilmRequest getCorrectFilm() {
        return FilmRequest.builder()
                .name("The Great Gatsby")
                .description("nice nice")
                .duration(125)
                .releaseDate("2013-10-10")
                .build();
    }
}
