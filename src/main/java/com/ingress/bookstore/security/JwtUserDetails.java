package com.ingress.bookstore.security;

import com.ingress.bookstore.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class JwtUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String username;
    private String password;

    public JwtUserDetails(Long id, String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(User user)
    {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if ("STUDENT".equals(user.getRole())) {
            authorityList.add(new SimpleGrantedAuthority("STUDENT"));
        }

        if ("AUTHOR".equals(user.getRole())) {
            authorityList.add(new SimpleGrantedAuthority("AUTHOR"));
        }

        return new JwtUserDetails(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(), authorityList);
    }

    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
