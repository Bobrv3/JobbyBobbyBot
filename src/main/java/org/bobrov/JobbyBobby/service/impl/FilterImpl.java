package org.bobrov.JobbyBobby.service.impl;

import lombok.RequiredArgsConstructor;
import org.bobrov.JobbyBobby.dao.FilterRepository;
import org.bobrov.JobbyBobby.model.criteria.Filter;
import org.bobrov.JobbyBobby.service.FilterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterImpl implements FilterService {
    private final FilterRepository repo;


    @Override
    public Filter save(Filter filter) {
        return repo.save(filter);
    }

    @Override
    public List<Filter> findAll() {
        return repo.findAll();
    }

    @Override
    public void remove(Long id) {
        repo.deleteById(id);
    }
}
