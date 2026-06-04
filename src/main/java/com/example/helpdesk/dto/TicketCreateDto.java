package com.example.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TicketCreateDto {

    @NotBlank(message = "Введите имя")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    private String customerName;

    @NotBlank(message = "Введите тему заявки")
    @Size(max = 120, message = "Тема не должна превышать 120 символов")
    private String title;

    @NotBlank(message = "Опишите проблему")
    @Size(max = 2000, message = "Описание не должно превышать 2000 символов")
    private String description;

    // Геттеры и сеттеры
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}