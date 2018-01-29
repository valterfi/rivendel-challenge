package com.valterfi.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.valterfi.constant.Constants;
import com.valterfi.domain.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonView(Views.Public.class)
    private String id;
    
    @JsonView(Views.Public.class)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kind", nullable = true)
    private Kind kind;
    
    @JsonView(Views.Public.class)
    @Column(name = "description", nullable = true)
    private String description;
    
    @JsonView(Views.Public.class)
    @JsonFormat(pattern=Constants.JSON_FORMAT_DATE)
    @Column(name = "logged_at", nullable = true)
    private Date loggedAt;
    
    public Activity(String description, Date loggedAt) {
        this.description = description;
        this.loggedAt = loggedAt;
    }

}
