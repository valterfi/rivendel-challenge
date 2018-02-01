package com.valterfi.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valterfi.Application;
import com.valterfi.jpa.domain.Activity;
import com.valterfi.service.ActivityService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ActivityControllerTest {
    
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc activityControllerMvc;
    
    @Mock
    private ActivityService activityService;
    
    @Before
    public void setUp() {
        ActivityController activityController = new ActivityController(activityService);
        activityControllerMvc = standaloneSetup(activityController).build();
    }
    
    @Test
    public void testGetAll() throws Exception {
        
        List<Activity> activities = new ArrayList<Activity>();
        activities.add(new Activity("description1", new Date()));
        activities.add(new Activity("description2", new Date()));
        
        when(activityService.findAll()).thenReturn(activities);
        
        activityControllerMvc.perform(get("/api/activities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].description", is("description2")));

    }
    
    @Test
    public void testGet() throws Exception {
        
        Activity activity = new Activity("description1", new Date());
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(activity);
        
        activityControllerMvc.perform(get("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")));

    }
    
    @Test
    public void testGetNotFound() throws Exception {
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(null);
        
        activityControllerMvc.perform(get("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6"))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void testCreate() throws Exception {
        
        Activity activity = new Activity("description1", new Date());
        
        when(activityService.save(activity)).thenReturn(activity);
        
        activityControllerMvc.perform(post("/api/activities")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(activity)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")));

    }
    
    @Test
    public void testUpdate() throws Exception {
        
        Activity activity = new Activity("description1", new Date());
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(activity);
        when(activityService.save(activity)).thenReturn(activity);
        
        activityControllerMvc.perform(put("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(activity)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")));
        
    }
    
    @Test
    public void testUpdateNotFound() throws Exception {
        
        Activity activity = new Activity("description1", new Date());
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(null);
        
        activityControllerMvc.perform(put("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(activity)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
        
    }
    
    @Test
    public void testDelete() throws Exception {
        
        Activity activity = new Activity("description1", new Date());
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(activity);
        doNothing().when(activityService).delete(activity);
        
        activityControllerMvc.perform(delete("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6"))
                .andExpect(status().isOk());

    }
    
    @Test
    public void testDeleteNotFound() throws Exception {
        
        when(activityService.findOne("269e4303-ac23-484e-a2ec-0f074bccfbe6")).thenReturn(null);
        
        activityControllerMvc.perform(delete("/api/activities/269e4303-ac23-484e-a2ec-0f074bccfbe6"))
                .andExpect(status().isNotFound());

    }
    
    
    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
