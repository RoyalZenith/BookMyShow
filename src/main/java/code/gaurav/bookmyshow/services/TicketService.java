package code.gaurav.bookmyshow.services;

import code.gaurav.bookmyshow.dtos.BookResponseTicketDto;
import code.gaurav.bookmyshow.models.*;
import code.gaurav.bookmyshow.repositories.*;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    private static final int TIMEOUT = 5;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final TicketRepository ticketRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public TicketService(UserRepository userRepository, SeatRepository seatRepository,
                         ShowRepository showRepository, ShowSeatRepository showSeatRepository,
                         TicketRepository ticketRepository, ShowSeatTypeRepository showSeatTypeRepository){
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Long> seatIds, long showId, long userId) throws Exception {
        // 1. check the user, is user signup or not by checking in DB
        // 2. fetch seats based on seats Ids
        // 3. fetch the show based on
        // 4. fetch the show seats based on show and seats
        // 5. check for show seat availability
        // 5.a if not available throw some exception
        // 6. locked the ticket along with timestamp
        // 7. calculate the amount for all tickets
        // 8. create ticket object and return with inProgress status
        User user = userRepository.findById(userId);
        if(user == null){
            throw new Exception("User is not available");
        }

        Show show = showRepository.findById(showId);
        if(show == null){
            throw new Exception("show is not available");
        }
        List<Seat> seats = seatRepository.findAllById(seatIds);
//        List<ShowSeat> showSeats = getAndSetLock(seats,show);
        List<ShowSeat> showSeats = showSeatRepository.findAllBySeatInAndShow(seats,show);

        for(ShowSeat showSeat: showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)
                    || (showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED) &&
                    (Duration.between(showSeat.getLockedAt().toInstant(),new Date().toInstant())).toMinutes() > TIMEOUT)
            )){
                throw new Exception("Seat "+showSeat.getSeat().getSeatName() + "is not available");
            }
        }
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        Ticket ticket = new Ticket();
        ticket.setBookedBy(user);
        ticket.setShow(show);
        ticket.setTicketStatus(TicketStatus.BOOKED);
        ticket.setAmount(calculatedAmount(showSeats,show));
        ticket.setShowSeats(savedShowSeats);
        ticket.setTimeOfBooking(new Date());
        ticket.setCreatedOn(new Date());
        ticket.setAuditorium(show.getAuditorium());

        Ticket savedTicket =  ticketRepository.save(ticket);
        return savedTicket;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> getAndSetLock(List<Seat> seats, Show show) throws Exception {
        List<ShowSeat> showSeats = showSeatRepository.findAllBySeatInAndShow(seats,show);

        for(ShowSeat showSeat: showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)
                    || (showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED) &&
                    (Duration.between(showSeat.getLockedAt().toInstant(),new Date().toInstant())).toMinutes() > TIMEOUT)
            )){
                throw new Exception("Seat "+showSeat.getSeat().getSeatName() + "is not available");
            }
        }
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            showSeatRepository.save(showSeat);
        }
        return showSeats;
    }

    private double calculatedAmount(List<ShowSeat> showSeats, Show show) {
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        double amount = 0;
        for(ShowSeat showSeat:showSeats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }
        return amount;
    }
}
