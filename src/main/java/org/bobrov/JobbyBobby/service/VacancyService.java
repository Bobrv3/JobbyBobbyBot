package org.bobrov.JobbyBobby.service;

import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Criteria;

import java.util.List;

public interface VacancyService {
    void save(Vacancy vacancy);
    void saveAll(List<Vacancy> vacancies);
    Vacancy getById(Long id);
    List<Vacancy> getAll();
    List<Vacancy> searchOnHH(Criteria criteria);
}
