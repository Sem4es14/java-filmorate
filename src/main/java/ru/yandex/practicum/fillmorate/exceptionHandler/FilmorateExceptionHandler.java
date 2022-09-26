package ru.yandex.practicum.fillmorate.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.fillmorate.exception.ExceptionDTO;
import ru.yandex.practicum.fillmorate.exception.film.FilmNotFound;
import ru.yandex.practicum.fillmorate.exception.film.GenreNotFound;
import ru.yandex.practicum.fillmorate.exception.film.MpaNotFound;
import ru.yandex.practicum.fillmorate.exception.user.UserNotFound;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class FilmorateExceptionHandler {
    @ExceptionHandler(value = UserNotFound.class)
    public ResponseEntity<ExceptionDTO> userNotFound(UserNotFound e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ExceptionDTO(
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = FilmNotFound.class)
    public ResponseEntity<ExceptionDTO> filmNotFound(FilmNotFound e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = GenreNotFound.class)
    public ResponseEntity<ExceptionDTO> genreNotFound(GenreNotFound e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MpaNotFound.class)
    public ResponseEntity<ExceptionDTO> mpaNotFound(MpaNotFound e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }
}
