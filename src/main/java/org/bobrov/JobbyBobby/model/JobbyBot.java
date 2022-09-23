package org.bobrov.JobbyBobby.model;

import lombok.Data;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
public class JobbyBot extends TelegramWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;

    public JobbyBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("You sent: " + update.getMessage().getText())
                    .build();
        }

        return null;
    }
}
