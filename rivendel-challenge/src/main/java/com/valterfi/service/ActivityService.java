package com.valterfi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.valterfi.jpa.domain.Activity;
import com.valterfi.repository.ActivityRepository;

@Service
public class ActivityService {
    
    private final ActivityRepository activityRepository;

    public ActivityService(final ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    public List<Activity> findAll() {
        return activityRepository.findByDeleted(false);
    }
    
    public Activity findOne(String id) {
        return activityRepository.findOneByIdAndDeleted(id, false);
    }
    
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }
    
    public void delete(Activity activity) {
        activity.setDeleted(true);
        activityRepository.save(activity);
    }

}
