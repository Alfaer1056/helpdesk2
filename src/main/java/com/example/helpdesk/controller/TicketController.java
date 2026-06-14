package com.example.helpdesk.controller;

import com.example.helpdesk.dto.TicketCreateDto;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new TicketCreateDto());
        return "ticket-form";
    }

    @PostMapping
    public String createTicket(
            @Valid @ModelAttribute("ticket") TicketCreateDto ticketCreateDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ticket-form";
        }

        Ticket savedTicket = ticketService.createTicket(ticketCreateDto);
        return "redirect:/tickets/" + savedTicket.getId() + "/success";
    }

    @GetMapping("/{id}/success")
    public String showSuccessPage(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticket-success";
    }
}