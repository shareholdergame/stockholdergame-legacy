package com.stockholdergame.server.web.controller;

import com.google.common.collect.Lists;
import com.stockholdergame.server.dto.account.FriendFilterType;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.account.UsersList;
import com.stockholdergame.server.dto.game.CurrentTurnDto;
import com.stockholdergame.server.dto.game.UserStatisticsFilterDto;
import com.stockholdergame.server.dto.game.UserStatisticsList;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.facade.SocialFacade;
import com.stockholdergame.server.model.game.result.Statistics;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.web.dto.*;
import com.stockholdergame.server.web.dto.player.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "/", authorizations = { @Authorization("Bearer") }, tags = "Player API")
@Controller
@RequestMapping("/player")
public class PlayerController {

    private static Map<GameOptionFilter, String> statisticsVariantMapping = new HashMap<GameOptionFilter, String>() {
        {
            put(GameOptionFilter.game_4x6, Statistics.STATISTICS_10);
            put(GameOptionFilter.game_3x5, Statistics.STATISTICS_8);
        }
    };

    @Autowired
    private SocialFacade socialFacade;

    @Autowired
    private GameService gameService;

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

    @RequestMapping(value = "/statistics/{gameOptionFilter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<PlayerAchievementsResponse> playerStatistics(@PathVariable("gameOptionFilter") GameOptionFilter gameOptionFilter,
                                                                                      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                                                                      @RequestParam(value = "ipp", required = false, defaultValue = "10") int itemsPerPage) {
        UserStatisticsFilterDto userStatisticsFilterDto = new UserStatisticsFilterDto();
        userStatisticsFilterDto.setStatisticsVariant(statisticsVariantMapping.getOrDefault(gameOptionFilter, Statistics.STATISTICS_12));
        userStatisticsFilterDto.setOffset(offset);
        userStatisticsFilterDto.setMaxResults(itemsPerPage);
        UserStatisticsList userStatisticsList = socialFacade.getUserStatistics(userStatisticsFilterDto);
        return ResponseWrapper.ok(convertToPlayerAchievementsResponse(userStatisticsList, offset, itemsPerPage));
    }

    @RequestMapping(value = "/{playerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<PlayerWithLocation> getPlayer(@PathVariable("playerName") String playerName) {
        UserFilterDto userFilterDto = new UserFilterDto();
        userFilterDto.setUserName(playerName);
        UsersList usersList = socialFacade.getUsers(userFilterDto);
        if (usersList.getTotalCount() != 1) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, playerName);
        }

        return ResponseWrapper.ok(buildPlayerWithLocation(usersList.getUsers().get(0)));
    }

    @RequestMapping(value = "/whoplaysnow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<List<CurrentTurnDto>> currentTurns() {
        return ResponseWrapper.ok(gameService.getCurrentTurns());
    }

    private PlayerAchievementsResponse convertToPlayerAchievementsResponse(UserStatisticsList userStatisticsList, int offset, int itemsPerPage) {
        PlayerAchievementsResponse playerAchievementsResponse = new PlayerAchievementsResponse();
        playerAchievementsResponse.pagination = Pagination.of((int) userStatisticsList.getTotalCount(), offset, itemsPerPage);
        playerAchievementsResponse.items = Lists.newArrayList();
        userStatisticsList.getUserStatistics().forEach(userStatistics -> {
            Player player = new Player();
            player.name = userStatistics.getUser().getUserName();

            Location location = new Location();
            location.city = userStatistics.getUser().getProfile().getCity();
            location.country = userStatistics.getUser().getProfile().getCountry();

            PlayerSession playerSession = new PlayerSession();
            playerSession.lastPlay = Optional.ofNullable(userStatistics.getDaysAfterLastPlay()).orElse(0L);
            playerSession.lastVisit = Optional.ofNullable(userStatistics.getDaysAfterLastVisit()).orElse(0L);

            Achievements achievements = new Achievements();
            achievements.totalPlayed = Optional.ofNullable(userStatistics.getGameSeriesCount()).orElse(0);
            achievements.wins = Optional.ofNullable(userStatistics.getGameSeriesWinsCount()).orElse(0);
            achievements.draws = Optional.ofNullable(userStatistics.getDrawsCount()).orElse(0);
            achievements.bankrupts = Optional.ofNullable(userStatistics.getBankruptsCount()).orElse(0);
            achievements.winPercent = Optional.ofNullable(userStatistics.getWinsRatio()).orElse(0.0);
            achievements.maxTotalSum = Optional.ofNullable(userStatistics.getMaxTotal()).orElse(0);
            achievements.maxWonSum = Optional.ofNullable(userStatistics.getMaxDiff()).orElse(0);
            achievements.totalWonSum = Optional.ofNullable(userStatistics.getTotalWinned()).orElse(0);

            PlayerAchievements playerAchievements = new PlayerAchievements();
            playerAchievements.player = player;
            playerAchievements.location = location;
            playerAchievements.playerSession = playerSession;
            playerAchievements.achievements = achievements;
            playerAchievementsResponse.items.add(playerAchievements);
        });
        return playerAchievementsResponse;
    }

    private PlayerListResponse convert(UsersList usersList, int offset, int itemsPerPage) {
        PlayerListResponse playerListResponse = new PlayerListResponse();
        playerListResponse.pagination = Pagination.of(usersList.getTotalCount(), offset, itemsPerPage);
        playerListResponse.players = usersList.getUsers().stream().map(this::buildPlayerWithLocation)
                .collect(Collectors.toList());
        return playerListResponse;
    }

    private PlayerWithLocation buildPlayerWithLocation(UserDto userDto) {
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
    }
}
