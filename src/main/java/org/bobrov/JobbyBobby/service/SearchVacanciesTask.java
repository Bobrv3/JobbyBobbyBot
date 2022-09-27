package org.bobrov.JobbyBobby.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * When the application has risen, the job search task is launched
 */
@Component
@RequiredArgsConstructor
public class SearchVacanciesTask extends TimerTask {
    private final HHclient hHclient;
    private final JobbyBot jobbyBot;

    @EventListener
    public void handleContextStart(ApplicationReadyEvent are) {
        Timer timer = new Timer(true);
        timer.schedule(this, 10_000, 5_000);
    }

    @Override
    @SneakyThrows
    public void run() {
        List<Vacancy> allVacancies = hHclient.getAllVacancies();

        for (Vacancy vacancy : allVacancies) {
            jobbyBot.execute(SendMessage.builder()
                    .chatId(jobbyBot.getChatId())
                    .text(vacancy.getAlternate_url())
                    .build());
        }
    }
}
