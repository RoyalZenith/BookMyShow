package code.gaurav.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Ticket extends BaseModel{
    @ManyToOne
    private Show show;
    @ManyToOne
    private Auditorium auditorium;
    private double amount;
    @ManyToMany
    private List<ShowSeat> showSeats;
    @ManyToMany
    private List<Payment> payments;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    private Date timeOfBooking;
    @ManyToOne
    private User bookedBy;
}
