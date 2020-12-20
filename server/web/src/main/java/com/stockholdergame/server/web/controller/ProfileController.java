package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.web.dto.Location;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.player.Player;
import com.stockholdergame.server.web.dto.player.PlayerPersonalInfo;
import com.stockholdergame.server.web.dto.player.PlayerProfile;
import com.stockholdergame.server.web.dto.player.PlayerWithLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseWrapper<PlayerProfile> getProfile() {
        MyAccountDto myAccountDto = accountService.getAccountInfo();
        return ResponseWrapper.ok(convertToPlayerProfile(myAccountDto));
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
        playerPersonalInfo.about = profile.getAbout();
        playerPersonalInfo.birthday = simpleDateFormat.format(profile.getBirthday());
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
