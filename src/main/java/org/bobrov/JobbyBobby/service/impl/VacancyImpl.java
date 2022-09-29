package org.bobrov.JobbyBobby.service.impl;

import lombok.RequiredArgsConstructor;
import org.bobrov.JobbyBobby.dao.VacancyRepository;
import org.bobrov.JobbyBobby.model.HHresponse;
import org.bobrov.JobbyBobby.model.Vacancy;
import org.bobrov.JobbyBobby.model.criteria.Criteria;
import org.bobrov.JobbyBobby.proxy.HHProxy;
import org.bobrov.JobbyBobby.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacancyImpl implements VacancyService {
    private final VacancyRepository repo;
    private final HHProxy hhProxy;

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

    @Override
    public List<Vacancy> searchOnHH(Criteria criteria) {
        HHresponse hHresponse = hhProxy.findVacancies(criteria.toParams());
        List<Vacancy> vacancies = hHresponse.getVacancies();

        if (hHresponse.getPages() > 1) {
            Map<String, String> params = criteria.toParams();

            for (int i = 1; i < hHresponse.getPages(); i++) {
                params.put("page", String.valueOf(i));
                Collections.addAll(vacancies,
                        hhProxy.findVacancies(params).getVacancies().toArray(new Vacancy[0]));
            }
        }

        return vacancies;
    }
}
