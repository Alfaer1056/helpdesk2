package com.example.helpdesk.controller;

import com.example.helpdesk.model.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("pageTitle", "Список заявок");
        return "tickets";
    }

    @GetMapping("/tickets/new")
    public String newTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findByStatus(TicketStatus.NEW));
        model.addAttribute("pageTitle", "Новые заявки");
        return "tickets";
    }

    @GetMapping("/tickets/search")
    public String searchTickets(@RequestParam(required = false) String customerName, Model model) {
        if (customerName != null && !customerName.isEmpty()) {
            model.addAttribute("tickets",
                    ticketRepository.findByCustomerNameContainingIgnoreCase(customerName));
            model.addAttribute("searchTerm", customerName);
        } else {
            model.addAttribute("tickets", ticketRepository.findAllByOrderByCreatedAtDesc());
        }
        model.addAttribute("pageTitle", "Поиск заявок");
        return "tickets";
    }
}