package com.stockholdergame.server.services.security;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.localization.MessageHolder;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.session.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.INTERNAL_SERVER_ERROR;

public class CustomUserDetailsService implements UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private GamerAccountDao gamerAccountDao;

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GamerAccount gamerAccount = gamerAccountDao.findByUniqueParameters(username);
        if (gamerAccount != null) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            GrantedAuthority ga = new SimpleGrantedAuthority(ROLE_USER);
            grantedAuthorities.add(ga);
            return new CustomUser(gamerAccount.getUserName(), gamerAccount.getPassword(), true,
                    true, true, true, grantedAuthorities,
                    UserSessionUtil.createUserInfo(gamerAccount));
        } else {
            throw new ApplicationException(MessageHolder.getMessage(LocaleRegistry.getDefaultLocale(), INTERNAL_SERVER_ERROR));
        }
    }
}
