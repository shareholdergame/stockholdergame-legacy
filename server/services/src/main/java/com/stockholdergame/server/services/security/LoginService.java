package com.stockholdergame.server.services.security;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.helpers.MD5Helper;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.localization.MessageHolder;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.session.UserSessionData;
import com.stockholdergame.server.session.UserSessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.USER_LOGGED_IN;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.INTERNAL_SERVER_ERROR;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;

/**
 * @author Alexander Savin
 *         Date: 14.8.2010 21.40.05
 */
@Service("loginService")
public class LoginService extends AbstractUserDetailsAuthenticationProvider {

    private static Logger LOGGER = LogManager.getLogger(LoginService.class);

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private UserSessionTrackingService userSessionTrackingService;

    @Autowired
    private MessageChannel exceptionHandlingChannel;

    private static final String ROLE_USER = "ROLE_USER";

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {
        String username = userDetails.getUsername();
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(username);
        String password = (String) usernamePasswordAuthenticationToken.getCredentials();

        if (MD5Helper.checkMD5hash(password, userDetails.getPassword())) {
            //UserSessionData userSessionData = UserSessionUtil.initUserSessionData(gamerAccount);
            //userSessionTrackingService.logNewSession(userSessionData);
            LOGGER.info(getMessage(USER_LOGGED_IN, username));
        } else {
            throw new BadCredentialsException(username);
        }
    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {
        GamerAccount gamerAccount;
        try {
            gamerAccount = gamerAccountDao.findByUniqueParameters(username);
        } catch (Exception e) {
            Message message = MessageBuilder.withPayload(e).build();
            exceptionHandlingChannel.send(message);
            throw new ApplicationException(MessageHolder.getMessage(LocaleRegistry.getDefaultLocale(), INTERNAL_SERVER_ERROR));
        }
        if (gamerAccount != null && !AccountStatus.REMOVED_COMPLETELY.equals(gamerAccount.getStatus())) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            GrantedAuthority ga = new SimpleGrantedAuthority(ROLE_USER);
            grantedAuthorities.add(ga);
            return new User(gamerAccount.getUserName(), gamerAccount.getPassword(), true, true, true, true, grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
