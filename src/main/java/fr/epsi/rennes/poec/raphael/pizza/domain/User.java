package fr.epsi.rennes.poec.raphael.pizza.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String role;
    private boolean checked = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(UserRole.valueOf(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.checked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.checked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.checked;
    }

    @Override
    public boolean isEnabled() {
        return this.checked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}