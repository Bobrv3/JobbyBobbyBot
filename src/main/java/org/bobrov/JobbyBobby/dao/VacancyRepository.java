package org.bobrov.JobbyBobby.dao;

import org.bobrov.JobbyBobby.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
