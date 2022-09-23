package org.bobrov.JobbyBobby.config;

import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.JobbyBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@PropertySource("classpath:/tokens.properties")
public class TelegramBotConfig {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;

    @Value("${telegrambot.userName}")
    private String userName;

    @Value("${telegrambot.botToken}")
    private String botToken;


    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder()
                .url(webHookPath)
                .build();
    }

    @Bean
    @SneakyThrows
    public JobbyBot telegramBot() {
        JobbyBot bot = new JobbyBot(new DefaultBotOptions());

        bot.setBotToken(botToken);
        bot.setBotUsername(userName);
        bot.setBotPath(webHookPath);

        return bot;
    }
}
