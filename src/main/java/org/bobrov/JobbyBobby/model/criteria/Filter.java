package org.bobrov.JobbyBobby.model.criteria;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Filter} class is designed to provide search by criteria
 */

@Data
@Entity
@Table(name = "filters")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private HashMap<String, SearchCriteria> criteria = new HashMap<>();

    public Filter() {
    }

    public void add(String searchCriteria, SearchCriteria value) {
        criteria.put(searchCriteria, value);
    }

    public void remove(String searchCriteria) {
        criteria.remove(searchCriteria);
    }

    public Map<String, SearchCriteria> getCriteria() {
        return criteria;
    }

    /**
     * Converts criteria to a parameter map for request
     * @return parameter map
     * @see org.bobrov.JobbyBobby.proxy.HHProxy#findVacancies(Map params)
     */
    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, SearchCriteria> entry : criteria.entrySet()) {
            params.put(entry.getKey(), entry.getValue().toString());
        }

        return params;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder("Осуществляется поиск вакансий по критериям:\n");
        for (SearchCriteria cr : criteria.values()) {
            msg.append(String.format("\uD83D\uDCCD %s - %s\n", cr.getTitle(), cr.getCriteriaName()));
        }
        return msg.toString();
    }
}