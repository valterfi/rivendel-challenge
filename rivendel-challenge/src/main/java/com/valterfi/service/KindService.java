package com.valterfi.service;

import org.springframework.stereotype.Service;
import com.valterfi.repository.KindRepository;

@Service
public class KindService {
    
    private final KindRepository kindRepository;

    public KindService(final KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }

}
