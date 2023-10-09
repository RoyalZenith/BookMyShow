package code.gaurav.bookmyshow.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Language> languages;
    private String name;
    private double ratings;
    private double duration;

}
