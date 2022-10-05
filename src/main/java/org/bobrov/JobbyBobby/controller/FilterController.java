package org.bobrov.JobbyBobby.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.criteria.Filter;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.bobrov.JobbyBobby.service.FilterService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;

    @GetMapping
    @SneakyThrows
    public String getFilters(Model model){
        List<Filter> filters = filterService.findAll();
        model.addAttribute("filters", filters);

        return "filters-list";
    }

    @GetMapping("/menu")
    @SneakyThrows
    public String showFilterMenu(Model model){
        Class<?>[] classes = SearchCriteria.class.getClasses();
        for (Class<?> c : classes) {
            if (c.isEnum()) {
                model.addAttribute(c.getSimpleName(), c.getEnumConstants());
            } else {
                model.addAttribute(c.getSimpleName(), c.getDeclaredConstructor().newInstance());
            }
        }

        return "create-filter";
    }

    @PostMapping
    public String addFilter(@RequestParam Map<String,String> allRequestParams) {
        Filter filter = new Filter();
        ArrayList<SearchCriteria> criterias = filter.getCriteria();
        for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
            switch (entry.getKey().toUpperCase()) {
                case "TEXT": {
                    criterias.add(new SearchCriteria.TEXT(entry.getValue()));
                    break;
                }
                case "PERIOD": {
                    criterias.add(new SearchCriteria.PERIOD(Integer.parseInt(entry.getValue())));
                    break;
                }
                case "AREA": {
                    switch (Integer.parseInt(entry.getValue())) {
                        case 113: criterias.add(SearchCriteria.AREA.RUSSIA);
                        default: criterias.add(SearchCriteria.AREA.BELARUS);
                    }
                    break;
                }
                case "SEARCH_FIELD": {
                    criterias.add(SearchCriteria.SEARCH_FIELD.valueOf(entry.getValue()));
                    break;
                }
                case "EXPERIENCE": {
                    criterias.add(SearchCriteria.EXPERIENCE.valueOf(entry.getValue()));
                    break;
                }
            }
        }

        filterService.save(filter);

        return "index";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable Long id) {
        filterService.remove(id);
    }
}
