package org.bobrov.JobbyBobby.service;

import org.bobrov.JobbyBobby.model.Vacancy;

import java.util.List;

public interface VacancyService {
    void save(Vacancy vacancy);
    void saveAll(List<Vacancy> vacancies);
    Vacancy getById(Long id);
    List<Vacancy> getAll();
}
