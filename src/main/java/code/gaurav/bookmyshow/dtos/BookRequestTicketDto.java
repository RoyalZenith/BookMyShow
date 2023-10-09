package code.gaurav.bookmyshow.dtos;

import code.gaurav.bookmyshow.models.Show;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookRequestTicketDto {
    private List<Long> seatIds;
    private long showId;
    private long userId;
}
