package ru.otus.highload.hw.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@Getter
@Builder
public class UserLogin implements UserDetails {

    private Long id;

    private String login;

    private String password;

    private Long userId;

    public UserLogin(Long id, String login, String password, Long userId) {
        Preconditions.checkArgument(StringUtils.hasText(login), "login is required");
        Preconditions.checkArgument(StringUtils.hasText(password), "password is required");
        Preconditions.checkArgument(userId != null, "User id is required");
        this.id = id;
        this.login = login;
        this.password = password;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return login;
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
