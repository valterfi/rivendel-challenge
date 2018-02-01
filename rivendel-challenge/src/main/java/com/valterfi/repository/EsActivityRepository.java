package com.valterfi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.valterfi.elasticsearch.domain.EsActivity;

public interface EsActivityRepository extends ElasticsearchRepository<EsActivity, String>{

}
