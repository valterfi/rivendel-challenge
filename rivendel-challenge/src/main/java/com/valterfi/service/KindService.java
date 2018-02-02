package com.valterfi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.valterfi.jpa.domain.Kind;
import com.valterfi.repository.EsActivityCustomRepository;
import com.valterfi.repository.KindRepository;

@Service
public class KindService {
    
    private final KindRepository kindRepository;
    
    private final EsActivityCustomRepository esActivityCustomRepository;

    public KindService(final KindRepository kindRepository, final EsActivityCustomRepository esActivityRepository) {
        this.kindRepository = kindRepository;
        this.esActivityCustomRepository = esActivityRepository;
    }
    
    public List<Kind> findAll(){
        List<Kind> kinds = kindRepository.findByDeleted(false);
        kinds.forEach(kind -> {
            List<String> topTags = esActivityCustomRepository.findTopTags(kind.getId());
            if(!topTags.isEmpty()) {
                kind.setTags(topTags.stream().collect(Collectors.joining(" ")));
            }
        });
        return kinds;
    }
    
    public Kind findOne(String id) {
        Kind kind = kindRepository.findOneByIdAndDeleted(id, false);
        if(kind != null) {
            List<String> topTags = esActivityCustomRepository.findTopTags(kind.getId());
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
        kind.setDeleted(true);
        kindRepository.save(kind);
    }

}
