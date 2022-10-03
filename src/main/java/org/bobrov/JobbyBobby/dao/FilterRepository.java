package org.bobrov.JobbyBobby.dao;

import org.bobrov.JobbyBobby.model.criteria.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
}
