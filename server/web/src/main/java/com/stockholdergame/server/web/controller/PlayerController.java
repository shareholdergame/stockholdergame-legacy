package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.account.FriendFilterType;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.account.UsersList;
import com.stockholdergame.server.facade.SocialFacade;
import com.stockholdergame.server.web.dto.Location;
import com.stockholdergame.server.web.dto.Pagination;
import com.stockholdergame.server.web.dto.PlayerListResponse;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.player.Player;
import com.stockholdergame.server.web.dto.player.PlayerWithLocation;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

@Api(value = "/", authorizations = { @Authorization("Bearer") }, tags = "Player API")
@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private SocialFacade socialFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<PlayerListResponse> searchPlayer(
            @RequestParam(value = "name", required = false) @Nullable String playerNamePrefix,
            @RequestParam(value = "friend", required = false) @Nullable Boolean friend,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "ipp", required = false, defaultValue = "10") int itemsPerPage) {
        UserFilterDto userFilterDto = new UserFilterDto();
        userFilterDto.setUserName(playerNamePrefix);
        userFilterDto.setOffset(offset);
        userFilterDto.setMaxResults(itemsPerPage);
        if (friend != null && friend) {
            userFilterDto.setFriendFilters(new FriendFilterType[]{FriendFilterType.FRIENDS_ONLY, FriendFilterType.EXCLUDE_BOTS});
        }
        UsersList usersList = socialFacade.getUsers(userFilterDto);
        return ResponseWrapper.ok(convert(usersList, offset, itemsPerPage));
    }

    private PlayerListResponse convert(UsersList usersList, int offset, int itemsPerPage) {
        PlayerListResponse playerListResponse = new PlayerListResponse();
        playerListResponse.pagination = Pagination.of(usersList.getTotalCount(), offset, itemsPerPage);
        playerListResponse.players = usersList.getUsers().stream().map(userDto -> {
            PlayerWithLocation playerWithLocation = new PlayerWithLocation();
            Location location = new Location();
            if (userDto.getProfile() != null) {
                location.city = userDto.getProfile().getCity();
                location.country = userDto.getProfile().getCountry();
                location.stateProvince = userDto.getProfile().getRegion();
            }
            playerWithLocation.location = location;
            Player player = new Player();
            player.name = userDto.getUserName();
            player.removed = userDto.isRemoved();
            player.bot = userDto.isBot();
            player.friend = userDto.isFriend();
            player.online = userDto.isOnline();
            playerWithLocation.player = player;
            return playerWithLocation;
        }).collect(Collectors.toList());
        return playerListResponse;
    }
}
