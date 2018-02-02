package com.valterfi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.elasticsearch.domain.EsActivity;
import com.valterfi.jpa.domain.view.Views;
import com.valterfi.service.EsActivityService;;

@RestController
@RequestMapping(value = "/api/es/activities", produces = APPLICATION_JSON_VALUE)
public class EsActivityController {
    
    private final EsActivityService esActivityService;

    public EsActivityController(final EsActivityService esActivityService) {
        this.esActivityService = esActivityService;
    }
        
    @GetMapping("")
    @JsonView(Views.Public.class)
    public List<EsActivity> get(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, 
            @RequestParam("kind") String kind, 
            @RequestParam("description") String description) {
        
        return esActivityService.find(startDate, endDate, kind, description);
        
    }

}
