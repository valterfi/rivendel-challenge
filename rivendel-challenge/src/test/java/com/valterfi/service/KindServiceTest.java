package com.valterfi.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.valterfi.Application;
import com.valterfi.jpa.domain.Kind;
import com.valterfi.repository.EsActivityCustomRepository;
import com.valterfi.repository.KindRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class KindServiceTest {

    @InjectMocks
    private KindService kindService;

    @Mock
    private KindRepository kindRepository;

    @Mock
    private EsActivityCustomRepository esActivityCustomRepository;

    @Test
    public void testFindAll() {

        List<Kind> dataKinds = new ArrayList<Kind>();
        dataKinds.add(new Kind("3", "description1", "#FE5F55"));
        dataKinds.add(new Kind("4", "description2", "#F0B67F"));
        when(kindRepository.findAll()).thenReturn(dataKinds);

        List<String> topTags = new ArrayList<String>();
        topTags.add("coffee");
        topTags.add("beer");
        topTags.add("vodka");
        when(esActivityCustomRepository.findTopTags("3")).thenReturn(topTags);

        List<Kind> kinds = kindService.findAll();

        assertThat(kinds, allOf(hasItems(hasProperty("id", equalTo("3")),
                hasProperty("description", equalTo("description1")),
                hasProperty("color", equalTo("#FE5F55")),
                hasProperty("tags", equalTo("coffee beer vodka")),

                hasProperty("id", equalTo("4")),
                hasProperty("description", equalTo("description2")),
                hasProperty("color", equalTo("#F0B67F")), hasProperty("tags", equalTo("")))));

    }
    
    @Test
    public void testFindOne() {
        
        Kind dataKind = new Kind("3", "description1", "#FE5F55");
        when(kindRepository.findOne("3")).thenReturn(dataKind);
        
        List<String> topTags = new ArrayList<String>();
        topTags.add("coffee");
        topTags.add("beer");
        topTags.add("vodka");
        when(esActivityCustomRepository.findTopTags("3")).thenReturn(topTags);
        
        Kind kind = kindService.findOne("3");
        
        assertThat(kind.getId(), equalTo("3"));
        assertThat(kind.getDescription(), equalTo("description1"));
        assertThat(kind.getColor(), equalTo("#FE5F55"));
        assertThat(kind.getTags(), equalTo("coffee beer vodka"));
        
    }
    
    @Test
    public void testFindOneEmptyTopTags() {
        
        Kind dataKind = new Kind("3", "description1", "#FE5F55");
        when(kindRepository.findOne("3")).thenReturn(dataKind);
        
        List<String> topTags = new ArrayList<String>();
        topTags.add("coffee");
        topTags.add("beer");
        topTags.add("vodka");
        when(esActivityCustomRepository.findTopTags("3")).thenReturn(Collections.EMPTY_LIST);
        
        Kind kind = kindService.findOne("3");
        
        assertThat(kind.getId(), equalTo("3"));
        assertThat(kind.getDescription(), equalTo("description1"));
        assertThat(kind.getColor(), equalTo("#FE5F55"));
        assertThat(kind.getTags(), equalTo(""));
        
    }
    
    @Test
    public void testFindOneNull() {
        
        when(kindRepository.findOne("3")).thenReturn(null);
        Kind kind = kindService.findOne("3");
                
        assertThat(kind, nullValue());

        
    }

}
