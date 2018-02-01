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
import com.valterfi.jpa.domain.Kind;
import com.valterfi.repository.KindRepository;
import com.valterfi.service.KindService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class KindControllerTest {
    
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    
    private MockMvc kindControllerMvc;
    
    @Mock
    private KindRepository kindRepository;
    
    @Mock
    private KindService kindService;
    
    @Before
    public void setUp() {
        KindController kindController = new KindController(kindRepository, kindService);
        kindControllerMvc = standaloneSetup(kindController).build();
    }
    
    @Test
    public void testGetAll() throws Exception {
        
        List<Kind> kinds = new ArrayList<Kind>();
        kinds.add(new Kind("description1", "#FE5F55"));
        kinds.add(new Kind("description2", "#F0B67F"));
        
        when(kindService.findAll()).thenReturn(kinds);
        
        kindControllerMvc.perform(get("/api/kinds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].color", is("#FE5F55")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[1].color", is("#F0B67F")));

    }
    
    @Test
    public void testGet() throws Exception {
        
        Kind kind = new Kind("description1", "#FE5F55");
        
        when(kindRepository.findOne("1")).thenReturn(kind);
        
        kindControllerMvc.perform(get("/api/kinds/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")))
                .andExpect(jsonPath("$.color", is("#FE5F55")));

    }
    
    @Test
    public void testGetNotFound() throws Exception {
        
        when(kindRepository.findOne("1")).thenReturn(null);
        
        kindControllerMvc.perform(get("/api/kinds/1"))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void testCreate() throws Exception {
        
        Kind kind = new Kind("description1", "#FE5F55");
        
        when(kindRepository.save(kind)).thenReturn(kind);
        
        kindControllerMvc.perform(post("/api/kinds")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(kind)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")));

    }
    
    @Test
    public void testUpdate() throws Exception {
        
        Kind kind = new Kind("description1", "#FE5F55");
        
        when(kindRepository.findOne("1")).thenReturn(kind);
        when(kindRepository.save(kind)).thenReturn(kind);
        
        kindControllerMvc.perform(put("/api/kinds/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(kind)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("description1")))
                .andExpect(jsonPath("$.color", is("#FE5F55")));
        
    }
    
    @Test
    public void testUpdateNotFound() throws Exception {
        
        Kind kind = new Kind("description1", "#FE5F55");
        
        when(kindRepository.findOne("1")).thenReturn(null);
        
        kindControllerMvc.perform(put("/api/kinds/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(kind)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
        
    }
    
    @Test
    public void testDelete() throws Exception {
        
        Kind kind = new Kind("description1", "#FE5F55");
        
        when(kindRepository.findOne("1")).thenReturn(kind);
        doNothing().when(kindRepository).delete(kind);
        
        kindControllerMvc.perform(delete("/api/kinds/1"))
                .andExpect(status().isOk());

    }
    
    @Test
    public void testDeleteNotFound() throws Exception {
        
        when(kindRepository.findOne("1")).thenReturn(null);
        
        kindControllerMvc.perform(delete("/api/kinds/1"))
                .andExpect(status().isNotFound());

    }
    
    
    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
