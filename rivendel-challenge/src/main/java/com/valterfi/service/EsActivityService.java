package com.valterfi.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.valterfi.elasticsearch.domain.EsActivity;
import com.valterfi.repository.EsActivityRepository;

@Service
public class EsActivityService {
    
    private final EsActivityRepository esActivityRepository;

    public EsActivityService(final EsActivityRepository esActivityRepository) {
        this.esActivityRepository = esActivityRepository;
    }
    
    public List<EsActivity> find(Date startDate, Date endDate, String kind, String description) {
        return esActivityRepository.find(startDate, endDate, kind, description);
        
    }

}
