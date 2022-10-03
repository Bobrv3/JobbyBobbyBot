package org.bobrov.JobbyBobby.controller;

import lombok.SneakyThrows;
import org.bobrov.JobbyBobby.model.criteria.SearchCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    @SneakyThrows
    public String showMenu(Model model){
        Class<?>[] classes = SearchCriteria.class.getClasses();
        for (Class<?> c : classes) {
            if (c.isEnum()) {
                model.addAttribute(c.getSimpleName(), c.getEnumConstants());
            } else {
                model.addAttribute(c.getSimpleName(), c.getDeclaredConstructor().newInstance());
            }
        }

        return "index";
    }
}