package org.bobrov.JobbyBobby.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.dao.VacancyRepository;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Filter;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * When the application has risen, the job search task is launched
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Data
public class SearchVacanciesTask extends TimerTask {
    private final VacancyService vacancyService;
    private final VacancyRepository vacancyRepository;
    private final JobbyBot jobbyBot;
    private List<Filter> filters;
    private boolean firstSearch = true;
    @Value("${searchPeriod.minute}")
    private int timeInterval;

    @Override
    @SneakyThrows
    public void run() {
        List<Vacancy> foundVacancies = new ArrayList<>();

        if (firstSearch) {
            for (Filter filter : filters) {
                Collections.addAll(foundVacancies, vacancyService.searchOnHH(filter).toArray(new Vacancy[0]));
            }
            firstSearch = false;
        } else {
            for (Filter filter : filters) {
                filter.getCriteria()
                        .removeIf(criteria -> criteria instanceof SearchCriteria.PERIOD);
                filter.add(new SearchCriteria.DATE_FROM(LocalDateTime.now().minusMinutes(timeInterval)));

                Collections.addAll(foundVacancies, vacancyService.searchOnHH(filter).toArray(new Vacancy[0]));
            }
        }

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
        for (int i = 0; i < foundVacancies.size(); i++) {
            if (vacancyRepository.existsById(foundVacancies.get(i).getId())) {
                foundVacancies.remove(foundVacancies.get(i--));
            } else {
                vacancyService.save(foundVacancies.get(i));
            }
        }
    }
}
