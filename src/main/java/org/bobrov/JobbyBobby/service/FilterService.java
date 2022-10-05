package org.bobrov.JobbyBobby.service;

import org.bobrov.JobbyBobby.model.criteria.Filter;

import java.util.List;

public interface FilterService {
    Filter save(Filter filter);
    List<Filter> findAll();

    void remove(Long id);
}
