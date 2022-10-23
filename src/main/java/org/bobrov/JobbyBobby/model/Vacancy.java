package org.bobrov.JobbyBobby.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vacancy {
    @Id
    private Long id;
    private String alternate_url;
    private Timestamp published_at;

    private void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vacancy vacancy = (Vacancy) o;
        return id != null && Objects.equals(id, vacancy.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
