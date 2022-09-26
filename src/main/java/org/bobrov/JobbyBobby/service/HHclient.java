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

import java.util.List;

@Component
@RequiredArgsConstructor
public class HHclient {
    private static final String URL = "https://api.hh.ru/vacancies?text=java&area=16&search_field=name&experience=noExperience";
    private final RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    public List<Vacancy> getAllVacancies() {
        ResponseEntity<HHresponse> exchange = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        if (exchange.getStatusCode().equals(HttpStatus.OK)) {
            HHresponse hhResponse = exchange.getBody();
            return hhResponse.getVacancies();
        } else {
            throw new HhResponseNotReceived(exchange.getStatusCode() + " " + exchange.getStatusCodeValue());
        }
    }
}
