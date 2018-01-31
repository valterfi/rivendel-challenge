package com.valterfi.elasticsearch.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.fasterxml.jackson.annotation.  JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "rivendel", type = "activity")
public class EsActivity {
    
    @Id
    private String id;
    
    private String kind;
    
    private String description;
    
    @JsonProperty("logged_at")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date loggedAt;
    
    public EsActivity(String description, Date loggedAt) {
        this.description = description;
        this.loggedAt = loggedAt;
    }

}
