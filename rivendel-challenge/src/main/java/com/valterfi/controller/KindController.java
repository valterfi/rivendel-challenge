package com.valterfi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.domain.Kind;
import com.valterfi.domain.view.Views;
import com.valterfi.repository.KindRepository;

@RestController
@RequestMapping(value = "/api/kinds", produces = APPLICATION_JSON_VALUE)
public class KindController {
    
    private final KindRepository kindRepository;

    public KindController(final KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }
    
    @GetMapping("")
    @JsonView(Views.Kind.class)
    public List<Kind> getAll() {
        return kindRepository.findAll();
    }

}
