package com.example.munchly.utils;

import com.example.munchly.entities.User;
import com.example.munchly.entities.enums.Permissions;
import com.example.munchly.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.munchly.entities.enums.Permissions.*;
import static com.example.munchly.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permissions>> map = Map.of(
            CUSTOMER, Set.of(RESTAURANT_VIEW, CUSTOMER_CREATE, CUSTOMER_UPDATE, CUSTOMER_DELETE),
            ADMIN, Set.of(RESTAURANT_VIEW, RESTAURANT_CREATE, RESTAURANT_DELETE, RESTAURANT_UPDATE, CUSTOMER_VIEW)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }
}
