package com.example.demo.security;

import com.example.demo.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authoritites;
    private Boolean active;

    public MyUserDetails(User student) {

        this.username = student.getUsername();
        this.password = student.getPassword();
        this.authoritites = Arrays.stream(student.getAuthority().split(","))
                .map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
        this.active = student.isVerified();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritites;
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
        return active;
    }
}
