package org.bobrov.JobbyBobby.model.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code Filter} class is designed to provide search by criteria
 */

@Entity
@Table(name = "filters")
@NoArgsConstructor
@Getter
@Setter
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private ArrayList<SearchCriteria> criteria = new ArrayList<>();

    private void setId(Long id) {
        this.id = id;
    }

    public void add(SearchCriteria value) {
        criteria.add(value);
    }

    /**
     * Converts criteria to a parameter map for request
     *
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
        StringBuilder msg = new StringBuilder("");
        for (SearchCriteria cr : criteria) {
            msg.append(String.format("\uD83D\uDCCD %s - %s%n <br>", cr.getTitle(), cr.getCriteriaName()));
        }
        return msg.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Filter filter = (Filter) o;
        return id != null && Objects.equals(id, filter.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}