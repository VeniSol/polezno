package org.example.polezno.Entities;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Role {
    ADMIN(Set.of(Perms.ADMIN,Perms.USER)),
    USER(Set.of(Perms.USER));

    private final Set<Perms> perms;

    Role(Set<Perms> perms) {
        this.perms = perms;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Perms perms : getPerms()) {
            authorities.add(new SimpleGrantedAuthority(perms.getPermission()));
        }
        return authorities;
    }
}
