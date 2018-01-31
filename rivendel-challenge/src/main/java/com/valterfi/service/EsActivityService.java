package com.valterfi.service;

import static com.valterfi.util.DateUtil.convertDateToFormat;
import static org.elasticsearch.index.query.QueryBuilders.filteredQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import java.util.Date;
import java.util.List;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import com.valterfi.constant.Constants;
import com.valterfi.elasticsearch.domain.EsActivity;

@Service
public class EsActivityService {
    
    private final ElasticsearchTemplate esTemplate;

    public EsActivityService(final ElasticsearchTemplate esTemplate) {
        this.esTemplate = esTemplate;
    }
    
    public List<EsActivity> find(Date startDate, Date endDate, String kind, String description) {
        String strStartDate = convertDateToFormat(startDate,Constants.JSON_FORMAT_DATE);
        String strEndDate = convertDateToFormat(endDate, Constants.JSON_FORMAT_DATE);
        RangeQueryBuilder rangeQueryBuilder = rangeQuery("logged_at")
                .format(Constants.JSON_FORMAT_DATE)
                .gte(strStartDate)
                .lte(strEndDate);
        
        FilteredQueryBuilder builder = filteredQuery(matchQuery("description", description), termQuery("kind", kind));
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).withFilter(rangeQueryBuilder).build();
        
        return esTemplate.queryForList(searchQuery, EsActivity.class);
        
    }

}
