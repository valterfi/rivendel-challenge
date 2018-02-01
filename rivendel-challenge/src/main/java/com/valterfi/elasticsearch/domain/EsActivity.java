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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "rivendel", type = "activity")
public class EsActivity {
    
    @Id
    private String id;
    
    private String kind;
    
    private String description;
    
    @JsonProperty("logged_at")
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date loggedAt;

}
