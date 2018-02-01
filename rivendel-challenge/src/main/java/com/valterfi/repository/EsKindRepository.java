package com.valterfi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.valterfi.elasticsearch.domain.EsKind;

public interface EsKindRepository extends ElasticsearchRepository<EsKind, String>{

}
