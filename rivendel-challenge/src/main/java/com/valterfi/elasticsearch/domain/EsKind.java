package com.valterfi.elasticsearch.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valterfi.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "rivendel", type = "kind")
public class EsKind {
    
    @Id
    private String id;
    
    private String color;
    
    @JsonProperty("created_at")
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date createdAt;
    
    private String description;
    
    @JsonProperty("updated_at")
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date updatedAt;
    
    private boolean deleted = false;

}
