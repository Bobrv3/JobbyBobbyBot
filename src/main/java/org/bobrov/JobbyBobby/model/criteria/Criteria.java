package org.bobrov.JobbyBobby.model.criteria;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Criteria} class is designed to provide search by criteria
 */

@Data
public class Criteria {
    private Map<String, SearchCriteria> criteria = new HashMap<>();

    public Criteria() {
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

}