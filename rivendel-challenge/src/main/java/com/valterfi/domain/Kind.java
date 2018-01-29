package com.valterfi.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.constant.Constants;
import com.valterfi.domain.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "kind")
public class Kind {
    
    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private String id;
    
    @JsonView(Views.Public.class)
    @Column(name = "color", nullable = true)
    private String color;
    
    @JsonView(Views.Kind.class)
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    
    @JsonView(Views.Public.class)
    @Column(name = "description", nullable = true)
    private String description;
    
    @JsonView(Views.Kind.class)
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
    
    @JsonView(Views.Kind.class)
    @Transient
    private String tags = "";
    
    public Kind(String description, String color) {
        this.description = description;
        this.color = color;
        this.createdAt = new Date();
    }

}
