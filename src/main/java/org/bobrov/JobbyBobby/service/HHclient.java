package org.bobrov.JobbyBobby.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.exeption.HhResponseNotReceived;
import org.bobrov.JobbyBobby.model.HHresponse;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Criteria;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HHclient {
    private String URL = "https://api.hh.ru/vacancies?";
    private Criteria criteria;
    private final RestTemplate restTemplate = new RestTemplate();
    private final JobbyBot jobbyBot;

    @SneakyThrows
    public List<Vacancy> findVacancies() {
        ResponseEntity<HHresponse> exchange = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        if (exchange.getStatusCode().equals(HttpStatus.OK)) {
            HHresponse hhResponse = exchange.getBody();
            List<Vacancy> vacancies = hhResponse.getVacancies();

            if (hhResponse.getPages() > 1) {
                for (int i = 1; i < hhResponse.getPages(); i++) {
                    exchange = restTemplate.exchange(
                            String.format("%s&page=%s", URL, i),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<>() {
                            });
                    Collections.addAll(vacancies, exchange.getBody().getVacancies().toArray(new Vacancy[0]));
                }
            }

            return vacancies;
        } else {
            throw new HhResponseNotReceived("HhResponseNotReceived:" + exchange.getStatusCode() + " " + exchange.getStatusCodeValue());
        }
    }

    /**
     * Sets the search criteria and notifies the user about it
     * @param criteria
     */
    public void setSearchCriteria(Criteria criteria) {
        StringBuilder URLWithCriteria = new StringBuilder(this.URL);
        for (Map.Entry<String, SearchCriteria> entry : criteria.getCriteria().entrySet()) {
            URLWithCriteria.append(String.format("%s=%s&", entry.getKey(), entry.getValue().toString()));
        }

        URL = URLWithCriteria.toString();

        sendCriteriaInfo(criteria);
    }
    @SneakyThrows
    private void sendCriteriaInfo(Criteria criteria) {
        StringBuilder msg = new StringBuilder("Осуществляется поиск вакансий по критериям:\n");
        for (SearchCriteria cr : criteria.getCriteria().values()) {
            msg.append(String.format("\uD83D\uDCCD %s - %s\n", cr.getTitle(), cr.getCriteriaName()));
        }

        jobbyBot.execute(SendMessage.builder()
                .chatId(jobbyBot.getChatId())
                .text(msg.toString())
                .build());
    }
}
