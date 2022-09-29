package org.bobrov.JobbyBobby.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Criteria;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

/**
 * When the application has risen, the job search task is launched
 */
@Component
@RequiredArgsConstructor
@Data
public class SearchVacanciesTask extends TimerTask {
    private final VacancyService vacancyService;
    private final JobbyBot jobbyBot;
    @Value("${searchPeriod.minute}")
    private int timeInterval;

    @Override
    @SneakyThrows
    public void run() {
        // configure search criteria
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.TEXT.class.getSimpleName().toLowerCase(), new SearchCriteria.TEXT("java"));
        criteria.add(SearchCriteria.AREA.class.getSimpleName().toLowerCase(), SearchCriteria.AREA.Belarus);
        criteria.add(SearchCriteria.SEARCH_FIELD.class.getSimpleName().toLowerCase(),  SearchCriteria.SEARCH_FIELD.name);
        criteria.add(SearchCriteria.EXPERIENCE.class.getSimpleName().toLowerCase(),  SearchCriteria.EXPERIENCE.noExperience);
        criteria.add(SearchCriteria.DATE_FROM.class.getSimpleName().toLowerCase(),
                new SearchCriteria.DATE_FROM(LocalDateTime.now().minusMinutes(timeInterval)));

        List<Vacancy> foundVacancies = vacancyService.searchOnHH(criteria);

        if (!foundVacancies.isEmpty()) {
            checkNewVacancies(foundVacancies);
            sendNewVacancies(foundVacancies);
        }
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
