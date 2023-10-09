package code.gaurav.bookmyshow.controllers;

import code.gaurav.bookmyshow.dtos.BookRequestTicketDto;
import code.gaurav.bookmyshow.dtos.BookResponseTicketDto;
import code.gaurav.bookmyshow.dtos.ResponseStatus;
import code.gaurav.bookmyshow.models.Ticket;
import code.gaurav.bookmyshow.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/ticket")
public class TicketController {

    public TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    public BookResponseTicketDto bookTicket(@RequestBody BookRequestTicketDto bookRequestTicketDto){
        BookResponseTicketDto bookResponseTicketDto = new BookResponseTicketDto();
        try {
            Ticket ticket = ticketService.bookTicket(bookRequestTicketDto.getSeatIds(), bookRequestTicketDto.getShowId(), bookRequestTicketDto.getUserId());
            bookResponseTicketDto.setTicketStatus(ticket.getTicketStatus());
            bookResponseTicketDto.setShowId(ticket.getShow().getId());
            bookResponseTicketDto.setAmount(ticket.getAmount());
            bookResponseTicketDto.setMovieName(ticket.getShow().getMovie().getName());
            bookResponseTicketDto.setAuditoriumName(ticket.getAuditorium().getName());
            bookResponseTicketDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
           log.error("Exception occurred while booking tickets",e);
           bookResponseTicketDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookResponseTicketDto;
    }

}
