package com.valterfi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.valterfi.jpa.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    
    List<Activity> findByDeleted(boolean deleted);
    
    Activity findOneByIdAndDeleted(String id, boolean deleted);

}
