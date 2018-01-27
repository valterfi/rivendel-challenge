package com.valterfi.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
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
    private String id;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "logged_at", nullable = false)
    private Date loggedAt;
    
    public Activity(String description, Date loggedAt) {
        this.description = description;
        this.loggedAt = loggedAt;
    }

}
