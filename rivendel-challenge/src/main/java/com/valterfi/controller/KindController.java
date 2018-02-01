package com.valterfi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.Date;
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
import com.valterfi.jpa.domain.Kind;
import com.valterfi.jpa.domain.view.Views;
import com.valterfi.service.KindService;

@RestController
@RequestMapping(value = "/api/kinds", produces = APPLICATION_JSON_VALUE)
public class KindController {
    
    private final KindService kindService;

    public KindController(final KindService kindService) {
        this.kindService = kindService;
    }
    
    @GetMapping("")
    @JsonView(Views.Kind.class)
    public List<Kind> getAll() {
        return kindService.findAll();
    }
    
    @GetMapping("{id}")
    @JsonView(Views.Kind.class)
    public ResponseEntity<Kind> get(@PathVariable("id") String id) {
        Kind kind = kindService.findOne(id);
        if(kind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(kind);
    }
    
    @PostMapping("")
    @JsonView(Views.Kind.class)
    public Kind create(@Valid @RequestBody Kind kind) {
        return kindService.save(kind);
    }
    
    @PutMapping("{id}")
    @JsonView(Views.Kind.class)
    public ResponseEntity<Kind> update(@PathVariable(value = "id") String id, @Valid @RequestBody Kind kindDetails) {
        Kind kind = kindService.findOne(id);
        if(kind == null) {
            return ResponseEntity.notFound().build();
        }
        
        kind.setColor(kindDetails.getColor());
        kind.setDescription(kindDetails.getDescription());
        kind.setUpdatedAt(new Date());

        Kind updatedKind = kindService.save(kind);
        return ResponseEntity.ok(updatedKind);
    }
    
    @DeleteMapping("{id}")
    @JsonView(Views.Kind.class)
    public ResponseEntity<Kind> delete(@PathVariable(value = "id") String id) {
        Kind kind = kindService.findOne(id);
        if(kind == null) {
            return ResponseEntity.notFound().build();
        }

        kindService.delete(kind);
        return ResponseEntity.ok().build();
    }

}
