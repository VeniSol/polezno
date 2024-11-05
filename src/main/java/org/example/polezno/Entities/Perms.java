package org.example.polezno.Entities;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Perms {
    ADMIN("ADMIN"),
    USER("USER");
    private final String permission;
}
