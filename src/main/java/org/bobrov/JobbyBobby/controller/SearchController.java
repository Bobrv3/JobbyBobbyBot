package org.bobrov.JobbyBobby.controller;

import lombok.RequiredArgsConstructor;
import org.bobrov.JobbyBobby.service.FilterService;
import org.bobrov.JobbyBobby.service.SearchVacanciesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Timer;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final FilterService filterService;
    private Timer timer;
    @Autowired
    private ApplicationContext context;

    @GetMapping
    public String startSearch() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        timer = new Timer();

        SearchVacanciesTask task = context.getBean(SearchVacanciesTask.class);
        task.setFilters(filterService.findAll());

        timer.schedule(task, 0, task.getTimeInterval() * 60 * 1000);

        return "index";
    }
}
