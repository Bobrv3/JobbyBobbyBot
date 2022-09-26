package org.bobrov.JobbyBobby.service;

import lombok.Data;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "telegrambot")
public class JobbyBot extends TelegramWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;
    private String webHookPath;
    private String chatId = "388469857";

    @Autowired
    private HHclient hHclient;

    public JobbyBot() {
        super(new DefaultBotOptions());
    }

    @SneakyThrows
    @PostConstruct
    private void initialize() {
        this.setBotToken(botToken);
        this.setBotUsername(botUsername);
        this.setBotPath(botPath);
        this.setWebhook(SetWebhook.builder()
                .url(webHookPath)
                .build());
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            List<Vacancy> allVacancies = hHclient.getAllVacancies();

            for (Vacancy vacancy : allVacancies) {
                execute(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(vacancy.getAlternate_url())
                        .build());
            }
        }
        return null;
    }
}
