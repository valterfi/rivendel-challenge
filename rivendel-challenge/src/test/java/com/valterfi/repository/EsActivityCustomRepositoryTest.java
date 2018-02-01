package com.valterfi.repository;

import static com.valterfi.util.DateUtil.parseDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.valterfi.Application;
import com.valterfi.elasticsearch.domain.EsActivity;
import com.valterfi.elasticsearch.domain.EsKind;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EsActivityCustomRepositoryTest {

    @Autowired
    private EsActivityCustomRepository esActivityCustomRepository;

    @Autowired
    private EsActivityRepository esActivityRepository;
    
    @Autowired
    private EsKindRepository esKindRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(EsActivity.class);
        esTemplate.createIndex(EsActivity.class);
        esTemplate.putMapping(EsActivity.class);
        esTemplate.refresh(EsActivity.class);
        
        esKindRepository.save(new EsKind("3", "#F0B67F", parseDate("2018-09-23"), "DRINK", parseDate("2018-09-23")));

        esActivityRepository.save(new EsActivity("85d2c840-82d4-47d1-a2b8-1263451d7be5", "3", "water", parseDate("2018-01-25")));
        esActivityRepository.save(new EsActivity("e33a3412-0787-11e8-ba89-0ed5f89f718b", "3", "coffee", parseDate("2018-01-25")));
        esActivityRepository.save(new EsActivity("ebc73242-0787-11e8-ba89-0ed5f89f718b", "3", "coffee", parseDate("2018-01-25")));
        esActivityRepository.save(new EsActivity("f6d188f4-0787-11e8-ba89-0ed5f89f718b", "3", "beer", parseDate("2018-01-25")));
        esActivityRepository.save(new EsActivity("051b0cc8-0788-11e8-ba89-0ed5f89f718b", "3", "vodka", parseDate("2018-01-25")));
        esActivityRepository.save(new EsActivity("0cebc758-0788-11e8-ba89-0ed5f89f718b", "3", "whisky", parseDate("2018-01-25")));
    }

    @Test
    public void testFind() {

        List<EsActivity> activities = esActivityCustomRepository.find(parseDate("2018-01-24"),
                parseDate("2018-01-26"), "3", "coffee");
        assertThat(activities, not(empty()));
        assertThat(activities,
                allOf(hasItems(hasProperty("id", equalTo("ebc73242-0787-11e8-ba89-0ed5f89f718b")),
                        hasProperty("kind", equalTo("3")),
                        hasProperty("description", equalTo("coffee")),

                        hasProperty("id", equalTo("e33a3412-0787-11e8-ba89-0ed5f89f718b")),
                        hasProperty("kind", equalTo("3")),
                        hasProperty("description", equalTo("coffee")))));

    }
    
    @Test
    public void testFindTopTags() {
        
        List<String> topTags = esActivityCustomRepository.findTopTags("3");
        assertThat(topTags, not(empty()));
        assertThat(topTags, containsInAnyOrder("coffee", "beer", "vodka"));
        
    }

}
