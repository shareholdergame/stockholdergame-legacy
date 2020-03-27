package com.stockholdergame.server.services.security;

import com.stockholdergame.server.session.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private UserInfo userInfo;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                      UserInfo userInfo) {
        super(username, password, authorities);
        this.userInfo = userInfo;
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
