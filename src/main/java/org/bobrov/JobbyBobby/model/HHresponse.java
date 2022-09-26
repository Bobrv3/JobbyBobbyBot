package org.bobrov.JobbyBobby.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HHresponse {
    @JsonSetter("items")
    private List<Vacancy> vacancies;
    private int found;
    private int pages;
    private int page;
}
