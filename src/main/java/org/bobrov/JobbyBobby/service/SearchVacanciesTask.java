package org.bobrov.JobbyBobby.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Criteria;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * When the application has risen, the job search task is launched
 */
@Component
@RequiredArgsConstructor
public class SearchVacanciesTask extends TimerTask {
    private final VacancyService vacancyService;
    private final HHclient hHclient;
    private final JobbyBot jobbyBot;

    @EventListener
    @SneakyThrows
    public void handleContextStart(ApplicationReadyEvent are) {
        // configure hhClient search criteria
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Text.class.getSimpleName().toLowerCase(), new SearchCriteria.Text("java"));
        criteria.add(SearchCriteria.Area.class.getSimpleName().toLowerCase(), SearchCriteria.Area.Belarus);  // Belarus
        criteria.add(SearchCriteria.Search_field.class.getSimpleName().toLowerCase(),  SearchCriteria.Search_field.name);
        criteria.add(SearchCriteria.Experience.class.getSimpleName().toLowerCase(),  SearchCriteria.Experience.noExperience);
        hHclient.setSearchCriteria(criteria);

        // start executing SearchVacanciesTask
        Timer timer = new Timer(true);
        timer.schedule(this, 0, 30 * 60 * 1000);  // every 30min
    }

    @Override
    @SneakyThrows
    public void run() {
        List<Vacancy> foundVacancies = hHclient.findVacancies();

        checkNewVacancies(foundVacancies);

        sendNewVacancies(foundVacancies);
    }

    private void sendNewVacancies(List<Vacancy> foundVacancies) throws TelegramApiException {
        for (Vacancy vacancy : foundVacancies) {
            jobbyBot.execute(SendMessage.builder()
                    .chatId(jobbyBot.getChatId())
                    .text(vacancy.getAlternate_url())
                    .build());
        }
    }

    /**
     * If a vacancy has already been received before, then it is removed from the foundVacancies
     * @param foundVacancies
     */
    private void checkNewVacancies(List<Vacancy> foundVacancies) {
        List<Vacancy> savedVacancies = vacancyService.getAll();

        for (int i = 0; i < foundVacancies.size(); i++) {
            if (savedVacancies.contains(foundVacancies.get(i))) {
                foundVacancies.remove(foundVacancies.get(i--));
            } else {
                vacancyService.save(foundVacancies.get(i));
            }
        }
    }
}