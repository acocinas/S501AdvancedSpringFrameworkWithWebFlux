package com.blackjack.blackjack_api.exception.handler;

import com.blackjack.blackjack_api.exception.GameNotFoundException;
import com.blackjack.blackjack_api.exception.InvalidActionException;
import com.blackjack.blackjack_api.exception.InvalidPlayException;
import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return Mono.just(new ErrorResponse("PLAYER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleGameNotFoundException(GameNotFoundException ex) {
        return Mono.just(new ErrorResponse("GAME_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPlayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidPlayException(InvalidPlayException ex) {
        return Mono.just(new ErrorResponse("INVALID_PLAY", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleException(Exception ex) {
        log.debug("Unhandled exception caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage() + " - " + (ex.getCause() != null ? ex.getCause() : "")));
    }

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidActionException(InvalidActionException ex) {
        return Mono.just(new ErrorResponse("INVALID_ACTION", ex.getMessage()));
    }
}