package code.gaurav.bookmyshow.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "shows")
public class Show extends BaseModel{
    @ManyToOne
    private Auditorium auditorium;

    @ManyToOne
    private Movie movie;

    private Date startTime;
    private Date endTime;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Feature> features;

    @Enumerated(EnumType.STRING)
    private Language languages;
}
