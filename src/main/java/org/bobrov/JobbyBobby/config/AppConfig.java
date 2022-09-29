package org.bobrov.JobbyBobby.config;

import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.proxy.HHProxy;
import org.bobrov.JobbyBobby.service.JobbyBot;
import org.bobrov.JobbyBobby.service.SearchVacanciesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Timer;

@Configuration
@EnableFeignClients(basePackageClasses = HHProxy.class)
public class AppConfig {
    @Autowired
    private JobbyBot bot;
    @Autowired
    private SearchVacanciesTask searchVacanciesTask;

    /**
     * start executing SearchVacanciesTask
     * @param event
     */
    @EventListener
    @SneakyThrows
    public void handleContextStart(ApplicationReadyEvent event) {
        Timer timer = new Timer(true);
        timer.schedule(searchVacanciesTask, 0, 30 * 60 * 1000);  // every 30min
    }

    @EventListener
    @SneakyThrows
    public void handleAppFailed(ContextClosedEvent event) {
        bot.execute(SendMessage.builder()
                .chatId(bot.getChatId())
                .text("❌ App failed ❌")
                .build());
    }
}
