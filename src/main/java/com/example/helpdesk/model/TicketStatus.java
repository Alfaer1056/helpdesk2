package com.example.helpdesk.model;

public enum TicketStatus {
    NEW("Новая"),
    IN_PROGRESS("В работе"),
    RESOLVED("Решена");

    private final String displayName;

    TicketStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}