package code.gaurav.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "seats")
public class Seat extends BaseModel{
    private String seatName;
    @Column(name = "rowz")
    private int row;
    @Column(name = "colz")
    private int col;
    @ManyToOne
    private SeatType seatType;
}
