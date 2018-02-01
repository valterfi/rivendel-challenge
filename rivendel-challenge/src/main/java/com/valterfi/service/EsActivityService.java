package com.valterfi.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.valterfi.elasticsearch.domain.EsActivity;
import com.valterfi.repository.EsActivityCustomRepository;

@Service
public class EsActivityService {
    
    private final EsActivityCustomRepository esActivityCustomRepository;

    public EsActivityService(final EsActivityCustomRepository esActivityCustomRepository) {
        this.esActivityCustomRepository = esActivityCustomRepository;
    }
    
    public List<EsActivity> find(Date startDate, Date endDate, String kind, String description) {
        return esActivityCustomRepository.find(startDate, endDate, kind, description);
        
    }

}
