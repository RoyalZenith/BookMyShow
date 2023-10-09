package code.gaurav.bookmyshow.dtos;

import code.gaurav.bookmyshow.models.Seat;
import code.gaurav.bookmyshow.models.Show;
import code.gaurav.bookmyshow.models.ShowSeat;
import code.gaurav.bookmyshow.models.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class BookResponseTicketDto {

    private ResponseStatus responseStatus;
    private String auditoriumName;
    private List<Long> showSeats;
    private double amount;
    private TicketStatus ticketStatus;
    private Long showId;
    private String movieName;
}
