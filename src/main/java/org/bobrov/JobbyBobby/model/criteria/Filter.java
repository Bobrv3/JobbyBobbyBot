package org.bobrov.JobbyBobby.model.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Filter} class is designed to provide search by criteria
 */

@Entity
@Table(name = "filters")
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private ArrayList<SearchCriteria> criteria = new ArrayList<>();

    public void add(SearchCriteria value) {
        criteria.add(value);
    }

    /**
     * Converts criteria to a parameter map for request
     * @return parameter map
     * @see org.bobrov.JobbyBobby.proxy.HHProxy#findVacancies(Map params)
     */
    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        for (SearchCriteria cr : criteria) {
            params.put(cr.getClass().getSimpleName().toLowerCase(), cr.toString());
        }

        return params;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder("Осуществляется поиск вакансий по критериям:\n");
        for (SearchCriteria cr : criteria) {
            msg.append(String.format("\uD83D\uDCCD %s - %s%n", cr.getTitle(), cr.getCriteriaName()));
        }
        return msg.toString();
    }
}