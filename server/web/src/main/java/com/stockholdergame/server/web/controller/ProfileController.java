package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.facade.AccountFacade;
import com.stockholdergame.server.facade.SocialFacade;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.web.dto.Location;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.player.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private AccountFacade accountFacade;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseWrapper<PlayerProfile> getProfile() {
        MyAccountDto myAccountDto = accountFacade.getAccountInfo();
        return ResponseWrapper.ok(convertToPlayerProfile(myAccountDto));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> updateProfile(@RequestBody ProfileUpdate profileUpdate) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setCity(profileUpdate.location.city);
        profileDto.setCountry(profileUpdate.location.country);
        profileDto.setRegion(profileUpdate.location.stateProvince);
        accountFacade.changeProfileData(profileDto);
        return ResponseWrapper.ok();
    }

    private PlayerProfile convertToPlayerProfile(MyAccountDto myAccountDto) {
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.playerWithLocation = buildPlayerWithLocation(myAccountDto);
        if (myAccountDto.getProfile() != null) {
            playerProfile.personalInfo = buildPersonalInfo(myAccountDto.getProfile());
        }
        return playerProfile;
    }

    private PlayerPersonalInfo buildPersonalInfo(ProfileDto profile) {
        PlayerPersonalInfo playerPersonalInfo = new PlayerPersonalInfo();
        playerPersonalInfo.about = Optional.ofNullable(profile.getAbout()).orElse(StringUtils.EMPTY);
        playerPersonalInfo.birthday = profile.getBirthday() != null ? simpleDateFormat.format(profile.getBirthday()) : StringUtils.EMPTY;
        return playerPersonalInfo;
    }

    private PlayerWithLocation buildPlayerWithLocation(MyAccountDto myAccountDto) {
        PlayerWithLocation playerWithLocation = new PlayerWithLocation();
        Location location = new Location();
        if (myAccountDto.getProfile() != null) {
            location.city = myAccountDto.getProfile().getCity();
            location.country = myAccountDto.getProfile().getCountry();
            location.stateProvince = myAccountDto.getProfile().getRegion();
        }
        playerWithLocation.location = location;
        Player player = new Player();
        player.name = myAccountDto.getUserName();
        player.bot = myAccountDto.isBot();
        playerWithLocation.player = player;
        return playerWithLocation;
    }
}
