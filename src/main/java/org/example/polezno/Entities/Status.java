package org.example.polezno.Entities;

import lombok.Getter;

@Getter

public enum Status {
    ACTIVE("ACTIVE"),
    ADOPTED("ADOPTED"),
    DELIVERED("DELIVERED");
    private final String status;

    Status(String status) {
        this.status = status;
    }
}
