package com.valterfi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valterfi.domain.Activity;
import com.valterfi.repository.ActivityRepository;

@RestController
@RequestMapping(value = "/api/activities", produces = APPLICATION_JSON_VALUE)
public class ActivityController {
    
    private final ActivityRepository activityRepository;

    public ActivityController(final ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @GetMapping("/")
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

}
