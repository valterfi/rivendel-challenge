package com.valterfi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.valterfi.jpa.domain.Kind;
import com.valterfi.repository.EsActivityRepository;
import com.valterfi.repository.KindRepository;

@Service
public class KindService {
    
    private final KindRepository kindRepository;
    
    private final EsActivityRepository esActivityRepository;

    public KindService(final KindRepository kindRepository, final EsActivityRepository esActivityRepository) {
        this.kindRepository = kindRepository;
        this.esActivityRepository = esActivityRepository;
    }
    
    public List<Kind> findAll(){
        List<Kind> kinds = kindRepository.findAll();
        
        kinds.forEach(kind -> {
            List<String> topTags = esActivityRepository.findTopTags(kind.getId());
            if(!topTags.isEmpty()) {
                kind.setTags(topTags.stream().collect(Collectors.joining(" ")));
            }
        });

        return kinds;
    }
    
    public Kind findOne(String id) {
        Kind kind = kindRepository.findOne(id);
        if(kind != null) {
            List<String> topTags = esActivityRepository.findTopTags(kind.getId());
            if(!topTags.isEmpty()) {
                kind.setTags(topTags.stream().collect(Collectors.joining(" ")));
            }
        }
        return kind;
    }
    
    public Kind save(Kind kind) {
        return kindRepository.save(kind);
    }
    
    public void delete(Kind kind) {
        kindRepository.delete(kind);
    }

}
