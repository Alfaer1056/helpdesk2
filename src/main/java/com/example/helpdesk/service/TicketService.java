package com.example.helpdesk.service;

import com.example.helpdesk.dto.TicketCreateDto;
import com.example.helpdesk.model.Ticket;
import java.util.List;

public interface TicketService {

    Ticket createTicket(TicketCreateDto ticketCreateDto);

    List<Ticket> getAllTickets();

    Ticket getTicketById(Long id);
}