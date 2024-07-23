package com.example.demo.config;

import com.example.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoUserDetails implements UserDetails {
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    public UserInfoUserDetails() {

    }
    public UserInfoUserDetails(User userInfo) {
        email=userInfo.getEmail();
        password=userInfo.getPassword();
//        authorities= Arrays.stream(userInfo.getRoles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
