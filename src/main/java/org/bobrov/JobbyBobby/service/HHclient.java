package org.bobrov.JobbyBobby.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.exeption.HhResponseNotReceived;
import org.bobrov.JobbyBobby.model.HHresponse;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HHclient {
    private static final String URL = "https://api.hh.ru/vacancies?text=java&area=16&search_field=name&experience=noExperience";
    private final RestTemplate restTemplate = new RestTemplate();

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
}
