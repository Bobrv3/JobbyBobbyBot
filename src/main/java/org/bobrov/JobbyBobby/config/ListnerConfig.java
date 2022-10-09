package org.bobrov.JobbyBobby.config;

import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.service.JobbyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Configuration
@Profile("prod")
public class ListnerConfig {
    @Autowired
    private JobbyBot bot;
//    @Autowired
//    private SearchVacanciesTask searchVacanciesTask;

    /**
     * start executing SearchVacanciesTask
     * @param event
     */
//    @EventListener
//    @SneakyThrows
//    public void handleContextStart(ApplicationReadyEvent event) {
//        Timer timer = new Timer(true);
//        timer.schedule(searchVacanciesTask, 0, searchVacanciesTask.getTimeInterval() * 60 * 1000);
//    }

    @EventListener
    @SneakyThrows
    public void handleAppFailed(ContextClosedEvent event) {
        bot.execute(SendMessage.builder()
                .chatId("388469857")
                .text("❌ App failed ❌")
                .build());
    }
}
