package com.stockholdergame.server.services.event.handler;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.ProfileDao;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.Profile;
import com.stockholdergame.server.model.common.UserLocation;
import com.stockholdergame.server.services.common.CountryDetectionService;
import com.stockholdergame.server.services.event.EventHandler;
import com.stockholdergame.server.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Account created event handler.
 */
@Component("accountCreatedHandler")
public class AccountCreatedHandler implements EventHandler<UserSessionData> {

    @Autowired
    private CountryDetectionService countryDetectionService;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Override
    @Transactional
    public void handle(Long userId, UserSessionData userSessionData) {
        UserLocation userLocation = countryDetectionService.detectUserLocation(userSessionData.getUserIPAddress());

        if (userLocation == null) {
            return;
        }

        Long gamerId = userSessionData.getUserInfo().getId();
        Profile profile = profileDao.findByPrimaryKey(gamerId);
        if (profile == null) {
            GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
            profile = new Profile(gamerAccount);
            gamerAccount.setProfile(profile);
            profileDao.create(profile);
        }

        profile.setDetectedCountry(userLocation.getCountry());
        profile.setDetectedRegion(userLocation.getRegion());
        profile.setDetectedCity(userLocation.getCity());

        if (StringUtils.isEmpty(profile.getCountry())) {
            profile.setCountry(userLocation.getCountry());
        }
        if (StringUtils.isEmpty(profile.getRegion())) {
            profile.setRegion(userLocation.getRegion());
        }
        if (StringUtils.isEmpty(profile.getCity())) {
            profile.setCity(userLocation.getCity());
        }

        profileDao.update(profile);
    }
}
