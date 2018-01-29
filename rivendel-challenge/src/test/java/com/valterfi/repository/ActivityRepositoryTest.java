package com.valterfi.repository;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.valterfi.Application;
import com.valterfi.domain.Activity;
import com.valterfi.repository.ActivityRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
@DataJpaTest
public class ActivityRepositoryTest {
    
    @Autowired
    ActivityRepository activityRepository;
    
    
    @Test
    public void test() throws Exception {
        
        List<Activity> activities1 =  activityRepository.findAll();
        
        activityRepository.save(new Activity());
        
        List<Activity> activities2 =  activityRepository.findAll();
        
        
    }

}
