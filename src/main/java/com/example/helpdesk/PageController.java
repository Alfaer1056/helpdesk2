package com.example.helpdesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Главная - Help Desk");
        model.addAttribute("welcomeMessage", "Добро пожаловать в службу поддержки!");
        model.addAttribute("currentTime",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас - Help Desk");
        model.addAttribute("companyName", "ТехноПоддержка");
        model.addAttribute("founded", "2020");
        model.addAttribute("employees", 25);
        model.addAttribute("clients", 150);
        model.addAttribute("mission", "Обеспечиваем качественную техническую поддержку 24/7");
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("title", "Контакты - Help Desk");
        model.addAttribute("email", "support@helpdesk.ru");
        model.addAttribute("phone", "+7 (495) 123-45-67");
        model.addAttribute("address", "г. Москва, ул. Программистов, д. 42");
        model.addAttribute("workingHours", "Пн-Пт: 9:00 - 21:00, Сб-Вс: 10:00 - 18:00");
        return "contacts";
    }
}