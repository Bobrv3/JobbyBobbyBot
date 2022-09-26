package org.bobrov.JobbyBobby.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "vacancies")
@Data
@NoArgsConstructor
public class Vacancy {
    @Id
    private Long id;
    private String alternate_url;
    private Timestamp published_at;
}
