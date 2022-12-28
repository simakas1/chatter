package lt.vaskevicius.chatter.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
        DEFAULT,
        ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
