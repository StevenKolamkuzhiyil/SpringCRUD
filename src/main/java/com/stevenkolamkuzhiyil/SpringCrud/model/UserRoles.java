package com.stevenkolamkuzhiyil.SpringCrud.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.stevenkolamkuzhiyil.SpringCrud.model.UserPermissions.*;

public enum UserRoles {
    USER(Sets.newHashSet(READ)),
    MANAGER(Sets.newHashSet(READ, WRITE)),
    ADMIN(Sets.newHashSet(READ, WRITE, DELETE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return getPermissions()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toSet());
    }

    public static UserRoles roleFromName(String roleName) {
        switch (roleName) {
            case "USER":
                return USER;
            case "MANAGER":
                return MANAGER;
            case "ADMIN":
                return ADMIN;
            default:
                throw new IllegalArgumentException("Invalid role name " + roleName);
        }
    }
}
