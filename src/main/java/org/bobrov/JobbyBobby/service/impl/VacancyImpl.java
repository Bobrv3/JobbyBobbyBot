package org.bobrov.JobbyBobby.service.impl;

import lombok.RequiredArgsConstructor;
import org.bobrov.JobbyBobby.dao.VacancyRepository;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyImpl implements VacancyService {
    private final VacancyRepository repo;

    @Override
    public void save(Vacancy vacancy) {
        repo.save(vacancy);
    }

    @Override
    public void saveAll(List<Vacancy> vacancies) {
        repo.saveAll(vacancies);
    }

    @Override
    public Vacancy getById(Long id) {
        return repo.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Vacancy with id=%s isn't found")));
    }

    @Override
    public List<Vacancy> getAll() {
        return repo.findAll();
    }
}
