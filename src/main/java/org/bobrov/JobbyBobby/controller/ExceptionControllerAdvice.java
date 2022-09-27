package org.bobrov.JobbyBobby.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.exeption.HhResponseNotReceived;
import org.bobrov.JobbyBobby.model.ErrorResponse;
import org.bobrov.JobbyBobby.service.JobbyBot;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {
    private final JobbyBot jobbyBot;

    @SneakyThrows
    @ExceptionHandler(HhResponseNotReceived.class)
    public void hhError(HhResponseNotReceived ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        jobbyBot.execute(SendMessage.builder()
                .chatId(jobbyBot.getChatId())
                .text(errorResponse.toString())
                .build());
    }
    @SneakyThrows
    @ExceptionHandler(RuntimeException.class)
    public void runtimeError(HhResponseNotReceived ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        jobbyBot.execute(SendMessage.builder()
                .chatId(jobbyBot.getChatId())
                .text(errorResponse.toString())
                .build());
    }
}
