package com.valterfi.elasticsearch.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.constant.Constants;
import com.valterfi.jpa.domain.view.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "rivendel", type = "activity")
public class EsActivity {
    
    @Id
    @JsonView(Views.Public.class)
    private String id;
    
    @JsonView(Views.Public.class)
    private String kind;
    
    @JsonView(Views.Public.class)
    private String description;
    
    @JsonProperty("logged_at")
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    @JsonView(Views.Public.class)
    private Date loggedAt;
    
    private boolean deleted = false;

}
