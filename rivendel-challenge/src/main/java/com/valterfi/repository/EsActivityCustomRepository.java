package com.valterfi.repository;

import static com.valterfi.constant.Constants.ACTIVITY_TYPE;
import static com.valterfi.constant.Constants.INDEX;
import static com.valterfi.constant.Constants.JSON_FORMAT_DATE;
import static com.valterfi.util.DateUtil.convertDateToFormat;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;
import com.valterfi.elasticsearch.domain.EsActivity;

@Repository
public class EsActivityCustomRepository {
    
    private final ElasticsearchTemplate esTemplate;

    public EsActivityCustomRepository(final ElasticsearchTemplate esTemplate) {
        this.esTemplate = esTemplate;
    }
    
    public List<EsActivity> find(Date startDate, Date endDate, String kind, String description) {
        String strStartDate = convertDateToFormat(startDate,JSON_FORMAT_DATE);
        String strEndDate = convertDateToFormat(endDate, JSON_FORMAT_DATE);
        RangeQueryBuilder rangeQueryBuilder = rangeQuery("logged_at")
                .format(JSON_FORMAT_DATE)
                .gte(strStartDate)
                .lte(strEndDate);
        
        QueryBuilder builder = QueryBuilders
                .boolQuery()
                .must(termQuery("kind", kind))
                .must(termQuery("deleted", false))
                .must(matchQuery("description", description));
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices(INDEX).withTypes(ACTIVITY_TYPE).withQuery(builder).withFilter(rangeQueryBuilder).build();
        
        return esTemplate.queryForList(searchQuery, EsActivity.class);
        
    }
    
    public List<String> findTopTags(String kind) {
        
        List<String> topTags = new ArrayList<String>();
               
        SearchResponse response = esTemplate.getClient().prepareSearch(INDEX).setTypes(ACTIVITY_TYPE)
                            .setQuery(matchQuery("kind", kind))
                            .addAggregation(terms("top-tags").field("description").size(3))
                            .execute().actionGet();
        
        Terms terms = response.getAggregations().get("top-tags");
        Collection<Terms.Bucket> buckets = terms.getBuckets();
        buckets.forEach(bucket -> topTags.add(bucket.getKeyAsString()));
        
        return topTags;
    }

}
