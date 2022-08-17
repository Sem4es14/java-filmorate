package ru.yandex.practicum.filmorate.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.ExceptionDTO;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFound;

import java.time.LocalDateTime;

@ControllerAdvice
public class FilmExceptionHandler {
    @ExceptionHandler(value = FilmNotFound.class)
    public ResponseEntity<ExceptionDTO> filmNotFound(FilmNotFound e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

}
