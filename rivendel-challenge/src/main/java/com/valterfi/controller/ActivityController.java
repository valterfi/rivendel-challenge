package com.valterfi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.domain.Activity;
import com.valterfi.domain.view.Views;
import com.valterfi.repository.ActivityRepository;

@RestController
@RequestMapping(value = "/api/activities", produces = APPLICATION_JSON_VALUE)
public class ActivityController {
    
    private final ActivityRepository activityRepository;

    public ActivityController(final ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @GetMapping("")
    @JsonView(Views.Public.class)
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }
    
    @GetMapping("{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<Activity> get(@PathVariable("id") String id) {
        Activity activity = activityRepository.findOne(id);
        if(activity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(activity);
    }
    
    @PostMapping("")
    public Activity create(@Valid @RequestBody Activity activity) {
        return activityRepository.save(activity);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Activity> update(@PathVariable(value = "id") String id, @Valid @RequestBody Activity activityDetails) {
        Activity activity = activityRepository.findOne(id);
        if(activity == null) {
            return ResponseEntity.notFound().build();
        }
        
        activity.setDescription(activityDetails.getDescription());
        activity.setKind(activityDetails.getKind());
        activity.setLoggedAt(activityDetails.getLoggedAt());

        Activity updatedActivity = activityRepository.save(activity);
        return ResponseEntity.ok(updatedActivity);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Activity> delete(@PathVariable(value = "id") String id) {
        Activity activity = activityRepository.findOne(id);
        if(activity == null) {
            return ResponseEntity.notFound().build();
        }

        activityRepository.delete(activity);
        return ResponseEntity.ok().build();
    }
    

}
