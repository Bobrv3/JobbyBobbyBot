package org.bobrov.JobbyBobby.controller;

import lombok.RequiredArgsConstructor;
import org.bobrov.JobbyBobby.service.JobbyBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/callback")
@RequiredArgsConstructor
public class WebHookController {
    private final JobbyBot jobbyBot;

    @PostMapping("/jobby")
    public void onUpdateReceived(@RequestBody Update update) {
        jobbyBot.onWebhookUpdateReceived(update);
    }
}
