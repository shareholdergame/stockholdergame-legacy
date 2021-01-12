package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.FinishedGameDao;
import com.stockholdergame.server.dao.FriendDao;
import com.stockholdergame.server.dao.FriendRequestDao;
import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dao.GameMapperDao;
import com.stockholdergame.server.dao.GameSeriesResultDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.InvitationDao;
import com.stockholdergame.server.dao.ScoreDao;
import com.stockholdergame.server.dao.UserSessionLogDao;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.game.*;
import com.stockholdergame.server.dto.game.event.UserNotification;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.dto.game.result.CompetitorDiffDto;
import com.stockholdergame.server.dto.game.result.CompetitorResultDto;
import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.dto.game.variant.CardGroupDto;
import com.stockholdergame.server.dto.game.variant.GameShareDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.UserSessionLog;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.CompetitorCard;
import com.stockholdergame.server.model.game.CompetitorMove;
import com.stockholdergame.server.model.game.CompetitorProjection;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameRulesVersion;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.model.game.Move;
import com.stockholdergame.server.model.game.MoveStep;
import com.stockholdergame.server.model.game.RelatedGameProjection;
import com.stockholdergame.server.model.game.SharePrice;
import com.stockholdergame.server.model.game.SharePricePk;
import com.stockholdergame.server.model.game.ShareQuantity;
import com.stockholdergame.server.model.game.ShareQuantityPk;
import com.stockholdergame.server.model.game.TotalScoreProjection;
import com.stockholdergame.server.model.game.archive.FinishedGame;
import com.stockholdergame.server.model.game.archive.FinishedGameCompetitor;
import com.stockholdergame.server.model.game.archive.Score;
import com.stockholdergame.server.model.game.result.CompetitorDiff;
import com.stockholdergame.server.model.game.result.CompetitorResult;
import com.stockholdergame.server.model.game.result.GameSeriesResult;
import com.stockholdergame.server.model.game.variant.GameVariant;
import com.stockholdergame.server.model.game.variant.Rounding;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.services.game.GameVariantService;
import com.stockholdergame.server.services.game.InvitationService;
import com.stockholdergame.server.services.game.PlayGameService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.services.messaging.MessagingService;
import com.stockholdergame.server.services.security.DeniedForRemovedUser;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.util.AMFHelper;
import com.stockholdergame.server.util.collections.ChunkHandler;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.stockholdergame.server.dao.util.IdentifierHelper.generateLongId;
import static com.stockholdergame.server.model.game.GameStatus.RUNNING;
import static com.stockholdergame.server.model.game.InvitationStatus.ACCEPTED;
import static com.stockholdergame.server.model.game.InvitationStatus.CREATED;
import static com.stockholdergame.server.model.game.StepType.ZERO_STEP;

/**
 * @author Alexander Savin
 *         Date: 22.3.2010 21.26.16
 */
@Service("gameService")
@MessageEndpoint
public class GameServiceImpl extends UserInfoAware implements GameService {

    public static final int INTERRUPT_GAME_CHUNK_SIZE = 10;
    public static final int PLAYING_USERS_MAX_RESULTS = 4;
    private static final String FIRST_GAME_LETTER = "A";

    @Value("${invitation.expiration.timeout}")
    private int invitationExpirationTimeout = 10080;

    @Value("${game.interruption.timeout}")
    private int gameInterruptionTimeout = 4320;

    @Value("${max.initiated.game.offers}")
    private int maxInitiatedGameOffers = 1;

    @Value("${max.initiated.game.invitations}")
    private int maxInitiatedGameInvitations = 100;

    @Value("${game.offer.timeout}")
    private int gameOfferTimeOut = 10;

    @Value("${invitation.timeout}")
    private int invitationTimeOut = 43200;

    @Autowired
    private GameVariantService gameVariantService;

    @Autowired
    private PlayGameService playGameService;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private FinishedGameDao finishedGameDao;

    @Autowired
    private GameSeriesResultDao gameSeriesResultDao;

    @Autowired
    private GameMapperDao gameMapperDao;

    @Autowired
    private InvitationDao invitationDao;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private UserSessionLogDao userSessionLogDao;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private MailPreparationService mailPreparationService;

    @Autowired
    private ScoreDao scoreDao;

    @Autowired
    private FriendDao friendDao;

    public void setGameVariantService(GameVariantService gameVariantService) {
        this.gameVariantService = gameVariantService;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void setInvitationDao(InvitationDao invitationDao) {
        this.invitationDao = invitationDao;
    }

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    public void setMessagingService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void setGameOfferTimeOut(int gameOfferTimeOut) {
        this.gameOfferTimeOut = gameOfferTimeOut;
    }

    public void setInvitationTimeOut(int invitationTimeOut) {
        this.invitationTimeOut = invitationTimeOut;
    }

    public void setMaxInitiatedGameOffers(int maxInitiatedGameOffers) {
        this.maxInitiatedGameOffers = maxInitiatedGameOffers;
    }

    public void setMaxInitiatedGameInvitations(int maxInitiatedGameInvitations) {
        this.maxInitiatedGameInvitations = maxInitiatedGameInvitations;
    }

    public void setInvitationExpirationTimeout(int invitationExpirationTimeout) {
        this.invitationExpirationTimeout = invitationExpirationTimeout;
    }

    public void setGameInterruptionTimeout(int gameInterruptionTimeout) {
        this.gameInterruptionTimeout = gameInterruptionTimeout;
    }

    public List<GameVariantDto> getGameVariants() {
        List<GameVariant> gameVariants = gameVariantService.getGameVariants();
        List<GameVariantDto> gameVariantDtoList = new ArrayList<>();
        for (GameVariant gameVariant : gameVariants) {
            GameVariantDto gameVariantDto = DtoMapper.map(gameVariant, GameVariantDto.class);
            gameVariantDtoList.add(gameVariantDto);
        }
        return gameVariantDtoList;
    }

    public GamesList getGames(GameFilterDto gameFilterDto) {
        GameStatus status = GameStatus.valueOf(gameFilterDto.getGameStatus());
        return gameMapperDao.findGamesByParameters(getCurrentUser().getId(),
                status,
                gameFilterDto.isOffer() ? GameInitiationMethod.GAME_OFFER : GameInitiationMethod.INVITATION,
                gameFilterDto.isInitiator(),
                gameFilterDto.isNotInitiator(),
                validGameVariantId(gameFilterDto.getGameVariantId()),
                getCurrentUser().getUserName().equalsIgnoreCase(gameFilterDto.getUserName()) ? null : gameFilterDto.getUserName(),
                gameFilterDto.getOffset(),
                gameFilterDto.getMaxResults(),
                gameFilterDto.isSmallAvatar(),
                gameFilterDto.isLegacyRules() ? GameRulesVersion.RULES_1_0 : GameRulesVersion.RULES_1_3,
                gameFilterDto.getPlayersNumber());
    }

    private Long validGameVariantId(Long gameVariantId) {
        List<GameVariantDto> gameVariants = getGameVariants();
        for (GameVariantDto gameVariant : gameVariants) {
            if (gameVariant.getId().equals(gameVariantId)) {
                return gameVariantId;
            }
        }
        return null;
    }

    @DeniedForRemovedUser
    public GameStatusDto joinToGame(Long gameId) {
        Game game = gameDao.findByPrimaryKey(gameId);
        if (game == null) {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }
        if (!GameStatus.OPEN.equals(game.getGameStatus())) {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_GAME_STATUS, gameId, game.getGameStatus());
        }

        Date current = new Date();

        Competitor competitor = new Competitor();
        competitor.setInitiator(false);
        competitor.setJoinedTime(current);
        competitor.setGamerId(getCurrentUser().getId());
        competitor.setGame(game);

        game.getCompetitors().add(competitor);

        Invitation invitation = invitationDao.findCreatedInvitationByGameIdAndInviteeName(gameId, getCurrentUser().getUserName());
        gameDao.update(game);
        if (invitation != null) {
            changeInvitationStatus(invitation, InvitationStatus.ACCEPTED);
        }

        if (isReadyToStart(game)) {
            startGame(game, null);
        }

        for (Competitor c : game.getCompetitors()) {
            if (c.getGamerId().equals(getCurrentUser().getId())) {
                continue;
            }

            if (c.getGamerAccount().getIsBot()) {
                Long[] ids = new Long[] {gameId, c.getGamerId()};
                publishEvent(BusinessEventBuilder.<Long[]>initBuilder().setType(BusinessEventType.PLAY_BOT).setPayload(ids).toEvent());
            } else {
                GameEventDto body = new GameEventDto(gameId, getCurrentUser().getUserName());
                body.setGameStatus(game.getGameStatus().name());
                body.setOffer(GameInitiationMethod.GAME_OFFER.equals(game.getInitiationMethod()));
                sendNotification(c.getGamerId(),
                        game.getGameStatus().equals(GameStatus.RUNNING) ? GameEventType.GAME_STARTED : GameEventType.USER_JOINED, body);

                GamerAccount recipient = gamerAccountDao.findByPrimaryKey(c.getGamerId());
                if (!userSessionTrackingService.isUserOnline(recipient.getUserName())) {
                    if (GameStatus.RUNNING.equals(game.getGameStatus())) {
                        mailPreparationService.prepareGameStartedMessage(recipient.getUserName(), recipient.getEmail(),
                            getCurrentUser().getUserName(), recipient.getLocale(), c.getMoveOrder() == 1);
                    } else {
                        mailPreparationService.prepareUserJoinedMessage(recipient.getUserName(), recipient.getEmail(),
                            getCurrentUser().getUserName(), recipient.getLocale());
                    }
                }
            }
        }

        return new GameStatusDto(game.getId(), game.getGameStatus().name());
    }

    private boolean isReadyToStart(Game game) {
        return game.getCompetitors().size() > 1 && invitationDao.countInvitationsByGameIdAndStatus(game.getId(), CREATED) == 0;
    }

    @DeniedForRemovedUser
    public GameStatusDto initiateGame(GameInitiationDto gameInitiationDto) {
        return initiateGame(gameInitiationDto, getCurrentUser().getId());
    }

    @Transactional
    public GameStatusDto initiateGame(GameInitiationDto gameInitiationDto, Long userId) {
        Long variantId = gameInitiationDto.getGameVariantId();
        GameVariantDto gv = gameVariantService.getGameVariantById(variantId);

        int maxOpenGames = gameInitiationDto.isOffer() ? maxInitiatedGameOffers : maxInitiatedGameInvitations;
        int openGameTimeOutMinutes = gameInitiationDto.isOffer() ? gameOfferTimeOut : invitationTimeOut;

        int userInitiatedGamesCount = gameDao.countUserInitiatedGamesByMethod(userId,
                gameInitiationDto.isOffer() ? GameInitiationMethod.GAME_OFFER : GameInitiationMethod.INVITATION);
        if (userInitiatedGamesCount >= maxOpenGames) {
            throw new BusinessException(BusinessExceptionType.USER_INITIATED_GAMES_EXCEEDED, maxOpenGames);
        }

        int competitorsQuantity = gameInitiationDto.getInvitedUsers() != null && !gameInitiationDto.getInvitedUsers().isEmpty()
            ? gameInitiationDto.getInvitedUsers().size() + 1 : gameInitiationDto.isOffer() ? 2 : gv.getMaxGamersQuantity();
        if (competitorsQuantity > gv.getMaxGamersQuantity()) {
            throw new BusinessException(BusinessExceptionType.COMPETITORS_NUMBER_EXCEEDED, competitorsQuantity);
        }

        Date current = new Date();

        GameSeries gameSeries = new GameSeries();
        Long gameSeriesId = generateLongId();
        gameSeries.setId(gameSeriesId);
        gameSeries.setGameVariantId(variantId);
        gameSeries.setSwitchMoveOrder(gameInitiationDto.isSwitchMoveOrder());
        gameSeries.setPlayWithBot(isBot(userId));
        gameSeries.setCompetitorsQuantity(competitorsQuantity);
        gameSeries.setCreatedTime(current);
        gameSeries.setRulesVersion(GameRulesVersion.RULES_1_3);

        Game game = new Game();
        game.setId(generateLongId());
        game.setMaxSharePrice(gv.getPriceScale().getMaxValue());
        game.setSharePriceStep(gv.getPriceScale().getScaleSpacing());
        game.setRounding(Rounding.U);
        game.setGameVariantId(variantId);
        game.setGameStatus(GameStatus.OPEN);
        game.setCreatedTime(current);
        game.setInitiationMethod(gameInitiationDto.isOffer() ? GameInitiationMethod.GAME_OFFER : GameInitiationMethod.INVITATION);
        game.setExpiredTime(DateUtils.addMinutes(current, openGameTimeOutMinutes));
        game.setCompetitorsQuantity(competitorsQuantity);
        game.setRulesVersion(GameRulesVersion.RULES_1_3);
        game.setGameLetter(FIRST_GAME_LETTER);

        game.setGameSeries(gameSeries);

        Competitor competitor = new Competitor();
        competitor.setInitiator(true);
        competitor.setJoinedTime(current);
        competitor.setGamerId(userId);
        competitor.setGame(game);

        game.setCompetitors(CollectionsUtil.newSet(competitor));

        if (gameInitiationDto.isPlayWithComputer()) {
            Competitor bot = new Competitor();
            bot.setInitiator(false);
            bot.setJoinedTime(current);
            bot.setGamerId(selectBotId());
            bot.setGame(game);
            game.getCompetitors().add(bot);
        }

        gameDao.create(game);

        if (gameInitiationDto.isPlayWithComputer()) {
            gameDao.flush();
            Long[] ids = new Long[2];
            for (Competitor c : game.getCompetitors()) {
                if (c.getGamerId() == -100L || c.getGamerId() == -200L) {
                    ids[1] = c.getGamerId();
                } else {
                    ids[0] = c.getGamerId();
                }
            }
            startGame(game, Arrays.asList(ids));
        } else {
            if (gameInitiationDto.getInvitedUsers() != null) {
                CreateInvitationDto ci = new CreateInvitationDto();
                ci.setGameId(game.getId());
                ci.setInviteeNames(gameInitiationDto.getInvitedUsers().toArray(new String[gameInitiationDto.getInvitedUsers().size()]));
                inviteUser(ci);
            }
        }

        return new GameStatusDto(game.getId(), gameSeriesId, game.getGameStatus().name());
    }

    private Long selectBotId() {
        Random r = new Random();
        int n = r.nextInt(10);
        return n % 2 == 0 ? -200L : -100L;
    }

    private Boolean isBot(Long userId) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(userId);
        return gamerAccount.getIsBot();
    }

    public GameDto getGameById(Long gameId) {
        UserInfo userInfo = getCurrentUser();
        return getGameById(gameId, userInfo);
    }

    public GameDto getGameById(Long gameId, UserInfo userInfo) {
        Long userId = userInfo.getId();
        GameDto gameDto;
        List<RelatedGameProjection> relatedGames;
        Game game = gameDao.findGameByIdAndUserId(gameId, userId);
        Long gameSeriesId;
        if (game == null) {
            FinishedGame finishedGame = finishedGameDao.findByIdAndUserId(gameId, userId);
            if (finishedGame == null) {
                throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
            } else {
                Map<Integer, String> userNamesMap = buildUserNameMap(finishedGame.getCompetitors());
                byte[] gameObject = finishedGame.getGameObject();
                gameSeriesId = finishedGame.getGameSeries().getId();
                gameDto = (GameDto) AMFHelper.deserializeFromAmf(gameObject);
                gameDto.setSwitchMoveOrder(finishedGame.getGameSeries().getSwitchMoveOrder());
                gameDto.setGameLetter(finishedGame.getGameLetter());
                gameDto.setGameSeriesId(finishedGame.getGameSeries().getId());

                for (CompetitorDto competitorDto : gameDto.getCompetitors()) {
                    competitorDto.setUserName(userNamesMap.get(competitorDto.getMoveOrder()));
                }

                relatedGames = finishedGameDao.findRelatedGameIds(gameId, gameSeriesId);
            }
        } else {
            gameSeriesId = game.getGameSeries().getId();
            relatedGames = gameDao.findRelatedGameIds(gameId, gameSeriesId);
            gameDto = convertGameToDto(game, userInfo);
        }

        Set<RelatedGame> relatedGameDtos = new HashSet<>();
        for (RelatedGameProjection relatedGame : relatedGames) {
            RelatedGame relatedGame1 = DtoMapper.map(relatedGame, RelatedGame.class);
            relatedGameDtos.add(relatedGame1);
        }
        gameDto.setRelatedGames(relatedGameDtos);
        Map<Long, RelatedGame> relatedGameMap = CollectionsUtil.convertToMap(relatedGameDtos, new CollectionsUtil.Closure<Long, RelatedGame>() {
            @Override
            public Long getKey(RelatedGame value) {
                return value.getGameId();
            }
        });

        GameSeriesResult gameSeriesResult = gameSeriesResultDao.findByPrimaryKey(gameSeriesId);
        if (gameSeriesResult != null) {
            Set<CompetitorResult> competitorResults = gameSeriesResult.getCompetitorResults();
            Set<CompetitorDiff> competitorDiffs = gameSeriesResult.getCompetitorDiffs();

            List<CompetitorResultDto> competitorResultDtoList = DtoMapper.mapList(new ArrayList<>(competitorResults), CompetitorResultDto.class);
            List<CompetitorDiffDto> competitorDiffDtoList = DtoMapper.mapList(new ArrayList<>(competitorDiffs), CompetitorDiffDto.class);

            gameDto.setCompetitorResults(new HashSet<CompetitorResultDto>());
            gameDto.setGameSeriesCompetitorResults(new HashSet<CompetitorResultDto>());
            gameDto.setCompetitorDiffs(new HashSet<CompetitorDiffDto>());
            gameDto.setGameSeriesCompetitorDiffs(new HashSet<CompetitorDiffDto>());

            for (CompetitorResultDto competitorResultDto : competitorResultDtoList) {
                if (competitorResultDto.getGameId() == null) {
                    gameDto.getGameSeriesCompetitorResults().add(competitorResultDto);
                } else if (competitorResultDto.getGameId().equals(gameId)) {
                    gameDto.getCompetitorResults().add(competitorResultDto);
                } else if (relatedGameMap.containsKey(competitorResultDto.getGameId())) {
                    relatedGameMap.get(competitorResultDto.getGameId()).getCompetitorResults().add(competitorResultDto);
                }
            }

            for (CompetitorDiffDto competitorDiffDto : competitorDiffDtoList) {
                if (competitorDiffDto.getGameId() == null) {
                    gameDto.getGameSeriesCompetitorDiffs().add(competitorDiffDto);
                } else if (competitorDiffDto.getGameId().equals(gameId)) {
                    gameDto.getCompetitorDiffs().add(competitorDiffDto);
                } else if (relatedGameMap.containsKey(competitorDiffDto.getGameId())) {
                    relatedGameMap.get(competitorDiffDto.getGameId()).getCompetitorDiffs().add(competitorDiffDto);
                }
            }
        }

        return gameDto;
    }

    private Map<Integer, String> buildUserNameMap(Set<FinishedGameCompetitor> competitors) {
        Map<Integer, String> map = new HashMap<>();
        for (FinishedGameCompetitor competitor : competitors) {
            String userName = gamerAccountDao.findByPrimaryKey(competitor.getId().getGamerId()).getUserName();
            map.put(competitor.getMoveOrder(), userName);
        }
        return map;
    }

    public void cancelGame(Long gameId) {
        Long currentUserId = getCurrentUser().getId();
        Game game = findGameByIdAndUser(gameId, currentUserId);
        if (!GameStatus.OPEN.equals(game.getGameStatus())) {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_GAME_STATUS, gameId, game.getGameStatus());
        }
        if (!isInitiator(game.getCompetitors(), currentUserId)) {
            throw new BusinessException(BusinessExceptionType.OPERATION_NOT_PERMITTED);
        }
        List<Invitation> invitations = invitationDao.findByGameId(gameId);
        List<GamerAccount> allInvolvedGamers = new ArrayList<>();
        List<String> playerNames = new ArrayList<>();
        for (Competitor competitor : game.getCompetitors()) {
            GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(competitor.getGamerId());
            allInvolvedGamers.add(gamerAccount);
            playerNames.add(gamerAccount.getUserName());
        }
        for (Invitation invitation : invitations) {
            if (invitation.getStatus().equals(InvitationStatus.CREATED)) {
                GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(invitation.getInviteeId());
                allInvolvedGamers.add(gamerAccount);
                playerNames.add(gamerAccount.getUserName());
            }
        }

        for (Invitation invitation : invitations) {
            if (invitation.getStatus().equals(InvitationStatus.CREATED)) {
                invitation.setStatus(InvitationStatus.CANCELLED);
                invitationDao.update(invitation);
            }
        }
        game.setGameStatus(GameStatus.CANCELLED);
        gameDao.update(game);

        for (GamerAccount gamerAccount : allInvolvedGamers) {
            if (gamerAccount.getId().equals(getCurrentUser().getId())) {
                continue;
            }

            GameEventDto body = new GameEventDto(gameId, getCurrentUser().getUserName());
            body.setGameStatus(game.getGameStatus().name());
            body.setOffer(GameInitiationMethod.GAME_OFFER.equals(game.getInitiationMethod()));
            sendNotification(gamerAccount.getId(), GameEventType.GAME_CANCELED, body);
            if (!userSessionTrackingService.isUserOnline(gamerAccount.getUserName())) {
                mailPreparationService.prepareGameCancelledMessage(gamerAccount.getUserName(), gamerAccount.getEmail(),
                    getCurrentUser().getUserName(), gameVariantService.getGameVariantById(game.getGameVariantId()).getMovesQuantity(),
                    game.getRounding(), playerNames, gamerAccount.getLocale());
            }
        }
    }

    public List<GameVariantSummary> getGameVariantsSummary() {
        List<GameVariantSummary> gameVariantSummaryList = gameMapperDao.countGamesByVariant(getCurrentUser().getId());
        for (GameVariantSummary gameVariantSummary : gameVariantSummaryList) {
            if (gameVariantSummary.getInitiationMethod().equals(GameInitiationMethod.GAME_OFFER.name())) {
                List<CompetitorProjection> playingUsers = gameDao.getPlayingUsers(gameVariantSummary.getGameVariantId(),
                        GameInitiationMethod.GAME_OFFER, PLAYING_USERS_MAX_RESULTS);
                List<UserDto> users = new ArrayList<>(playingUsers.size());
                for (CompetitorProjection playingUser : playingUsers) {
                    UserDto userDto = DtoMapper.map(playingUser, UserDto.class);
                    users.add(userDto);
                }
                gameVariantSummary.setPlayingUsers(users);
            }
        }
        return gameVariantSummaryList;
    }

    public GamerActivitySummary getGamerActivitySummary() {
        Long currentUserId = getCurrentUser().getId();

        GamerActivitySummary summary = new GamerActivitySummary();

        UserSessionLog userSessionLog = userSessionLogDao.findLastSession(currentUserId);
        if (userSessionLog != null) {
            summary.setLastSessionStart(userSessionLog.getStartTime());
            summary.setLastSessionEnd(userSessionLog.getEndTime());
        }

        summary.setGameOffersNumber(gameMapperDao.countGamesByParameters(currentUserId, GameStatus.OPEN, GameInitiationMethod.GAME_OFFER,
                false, true, null, null, GameRulesVersion.RULES_1_3, null));
        summary.setInvitationsNumber(gameMapperDao.countGamesByParameters(currentUserId, GameStatus.OPEN, GameInitiationMethod.INVITATION, true,
                true, null, null, GameRulesVersion.RULES_1_3, null));
        summary.setGamesInProgressNumber(gameMapperDao.countGamesByParameters(currentUserId, GameStatus.RUNNING, null, false, false, null, null,
                GameRulesVersion.RULES_1_3, null));
        summary.setFinishedGamesQuantity(gameMapperDao.countGamesByParameters(currentUserId, GameStatus.FINISHED, null, false, false, null, null,
                GameRulesVersion.RULES_1_3, null));

        int scorersQuantity = scoreDao.countScorers(currentUserId, null, new String[] {GameRulesVersion.RULES_1_3});
        summary.setScorersQuantity(scorersQuantity);

        Long friendsQuantity = friendDao.countFriends(currentUserId);
        summary.setFriendsQuantity(friendsQuantity.intValue());

        Integer friendRequestQuantity = friendRequestDao.countNewFriendRequests(currentUserId);
        summary.setFriendRequestsQuantity(friendRequestQuantity);

        return summary;
    }

    @Override
    public TotalScoreDto getScores(ScoreFilterDto scoreFilter) {
        Long currentUserId = getCurrentUser().getId();
        String[] rulesVersion = scoreFilter.isLegacyRules() ? new String[] {GameRulesVersion.RULES_1_0}
                : new String[] {GameRulesVersion.RULES_1_3};
        TotalScoreProjection totalScoreProjection = scoreDao.countTotalScore(currentUserId, rulesVersion);
        TotalScoreDto totalScoreDto = DtoMapper.map(totalScoreProjection, TotalScoreDto.class);
        if (!scoreFilter.isTotalScoreOnly()) {
            int scorersQuantity = scoreDao.countScorers(currentUserId, scoreFilter.getUserName(), rulesVersion);
            List<Score> scores = scoreDao.findByFirstGamerId(currentUserId, scoreFilter.getUserName(), rulesVersion, scoreFilter);
            List<ScoreDto> scoreDtos = new ArrayList<>();
            for (Score score : scores) {
                ScoreDto scoreDto = DtoMapper.map(score, ScoreDto.class);
                scoreDto.getUser().setOnline(isUserOnline(scoreDto.getUser().getUserName()));
                scoreDtos.add(scoreDto);
            }
            totalScoreDto.setTotalScores(scorersQuantity);
            totalScoreDto.setScores(scoreDtos);
        }
        return totalScoreDto;
    }

    @Override
    @Transactional
    public void dropExpiredGameOffers() {
        List<Game> games = gameDao.findExpiredGameOffers(new Date());
        for (Game game : games) {
            game.setGameStatus(GameStatus.CANCELLED);
            gameDao.update(game);

            GameVariantDto gameVariant = gameVariantService.getGameVariantById(game.getGameVariantId());
            for (Competitor competitor : game.getCompetitors()) {

                GameEventDto body = new GameEventDto(game.getId(), competitor.getGamerAccount().getUserName());
                body.setGameStatus(game.getGameStatus().name());
                body.setMovesQuantity(gameVariant.getMovesQuantity());
                body.setOffer(GameInitiationMethod.GAME_OFFER.equals(game.getInitiationMethod()));
                sendNotification(competitor.getGamerId(), GameEventType.GAME_CANCELED, body);
            }
        }
    }

    @Override
    @Transactional
    public void interruptGames() {
        // todo - send warning to user before

        ChunkHandler<Game> chunkHandler = new ChunkHandler<Game>() {
            @Override
            protected void process(Game game) {
                playGameService.removeGameFromRegistry(game.getId());
                game.setGameStatus(GameStatus.INTERRUPTED);
                game.getGameSeries().setFinishedTime(new Date());
                gameDao.update(game);

                Competitor currentCompetitor = detectCurrentMoveOrder(game);
                String currentCompetitorName = currentCompetitor.getGamerAccount().getUserName();

                GameVariantDto gameVariant = gameVariantService.getGameVariantById(game.getGameVariantId());
                for (Competitor competitor : game.getCompetitors()) {
                    GamerAccount gamerAccount = competitor.getGamerAccount();

                    GameEventDto body = new GameEventDto(game.getId(), currentCompetitorName);
                    body.setGameStatus(game.getGameStatus().name());
                    body.setMovesQuantity(gameVariant.getMovesQuantity());
                    body.setOffer(GameInitiationMethod.GAME_OFFER.equals(game.getInitiationMethod()));
                    sendNotification(competitor.getGamerId(), GameEventType.GAME_INTERRUPTED, body);

                    mailPreparationService.prepareGameInterruptedMessage(gamerAccount.getUserName(), gamerAccount.getEmail(),
                            gamerAccount.getLocale(), gameVariant.getName(), gameVariant.getMovesQuantity(),
                            currentCompetitor.getMoveOrder().equals(competitor.getMoveOrder()), currentCompetitorName,
                            (gameInterruptionTimeout / 60));
                }
            }

            @Override
            protected List<Game> find(int limit) {
                 Date date = DateUtils.addMinutes(new Date(), -gameInterruptionTimeout);
                return gameDao.findLongDrawnGames(date, 0, limit);
            }
        };
        chunkHandler.perform(INTERRUPT_GAME_CHUNK_SIZE);
    }

    private Competitor detectCurrentMoveOrder(Game game) {
        Move lastMove = null;
        Set<Move> moves = new TreeSet<>(game.getMoves());
        for (Move move : moves) {
            lastMove = move;
        }
        if (lastMove == null) {
            throw new ApplicationException("Can't detect current move order. No moves in game " + game.getId());
        }
        Set<CompetitorMove> competitorMoves = new TreeSet<>(lastMove.getCompetitorMoves());
        int lastMoveOrder = 1;
        for (CompetitorMove competitorMove : competitorMoves) {
            lastMoveOrder = competitorMove.getMoveOrder();
        }
        Competitor currentCompetitor = null;
        boolean lastFound = false;
        List<Competitor> competitors = new ArrayList<>(game.getCompetitors());
        competitors.addAll(game.getCompetitors());
        for (Competitor competitor : competitors) {
            if (lastFound && !competitor.getOut()) {
                currentCompetitor = competitor;
                break;
            }
            if (competitor.getMoveOrder() == lastMoveOrder) {
                lastFound = true;
            }
        }
        return currentCompetitor;
    }

    @Override
    public void tryToStartGames(Set<Long> gameIds) {
        if (gameIds == null) {
            return;
        }
        for (Long gameId : gameIds) {
            tryToStartGame(gameId);
        }
    }

    @Override
    @Transactional
    public void tryToStartGame(Long gameId) {
        Game game = gameDao.findByPrimaryKey(gameId);
        if (game == null) {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }
        if (!GameStatus.OPEN.equals(game.getGameStatus())) {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_GAME_STATUS, gameId, game.getGameStatus());
        }

        if (isReadyToStart(game)) {
            startGame(game, null);
        } else if (canBeCanceled(game)) {
            game.setGameStatus(GameStatus.CANCELLED);
            gameDao.update(game);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void startGameWithPredefinedMoveOrder(Long templateGameId) {
        Game templateGame = gameDao.findByPrimaryKey(templateGameId);

        Date current = new Date();

        Game game = new Game();
        game.setId(generateLongId());
        game.setMaxSharePrice(templateGame.getMaxSharePrice());
        game.setSharePriceStep(templateGame.getSharePriceStep());
        game.setRounding(templateGame.getRounding());
        game.setGameVariantId(templateGame.getGameVariantId());
        game.setGameStatus(GameStatus.OPEN);
        game.setCreatedTime(current);
        game.setInitiationMethod(templateGame.getInitiationMethod());
        game.setExpiredTime(DateUtils.addMinutes(current, invitationTimeOut));
        //game.setCompetitorsQuantity(templateGame.getCompetitorsQuantity());
        game.setRulesVersion(templateGame.getRulesVersion());
        game.setGameSeries(templateGame.getGameSeries());

        int nextValue = templateGame.getGameLetter().charAt(0) + 1;
        game.setGameLetter(new String(new char[]{(char) nextValue}));

        TreeSet<Competitor> templateGameCompetitors = new TreeSet<>(templateGame.getCompetitors());
        Set<Competitor> competitors = new HashSet<>();
        LinkedList<Long> gamerIds = new LinkedList<>();
        for (Competitor competitorTemplate : templateGameCompetitors) {
            Competitor competitor = new Competitor();
            competitor.setInitiator(competitorTemplate.getInitiator());
            competitor.setJoinedTime(current);
            competitor.setGamerId(competitorTemplate.getGamerId());
            competitor.setGame(game);
            competitors.add(competitor);
            gamerIds.add(competitorTemplate.getGamerId());
        }
        game.setCompetitors(competitors);

        // switch move order
        gamerIds.addLast(gamerIds.removeFirst());

        startGame(game, gamerIds);

        gameDao.create(game);

        for (Competitor competitor : game.getCompetitors()) {
            GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(competitor.getGamerId());
            if (gamerAccount.getIsBot()) {
                Long[] ids = new Long[] {game.getId(), competitor.getGamerId()};
                publishEvent(BusinessEventBuilder.<Long[]>initBuilder().setType(BusinessEventType.PLAY_BOT).setPayload(ids).toEvent());
            } else {
                GameEventDto body = new GameEventDto(game.getId(), gamerAccount.getUserName());
                body.setGameStatus(game.getGameStatus().name());
                body.setOffer(GameInitiationMethod.GAME_OFFER.equals(game.getInitiationMethod()));
                sendNotification(competitor.getGamerId(), GameEventType.GAME_STARTED, body);
            }
            // todo - send e-mail
        }
    }

    @Override
    public List<CurrentTurnDto> getCurrentTurns() {
        return gameMapperDao.getCurrentTurns();
    }

    @Override
    public GameStatusDto joinToGameByGameSetId(Long gameSetId) {
        List<Game> games = gameDao.findGamesByGameSeriesId(gameSetId);
        return joinToGame(getGameIdFromGameSeries(games));
    }

    private Long getGameIdFromGameSeries(List<Game> games) {
        return games.stream().findFirst().map(Game::getId)
                .orElseThrow(() -> new ApplicationException("Game Set is empty. At least one game is expected."));
    }

    private boolean canBeCanceled(Game game) {
        return game.getCompetitors().size() == 1 && invitationDao.countInvitationsByGameIdAndStatus(game.getId(), CREATED) == 0;
    }

    @DeniedForRemovedUser
    public void inviteUser(CreateInvitationDto createInvitationDto) {
        Long gameId = createInvitationDto.getGameId();
        String[] inviteeNames = createInvitationDto.getInviteeNames();

        for (String inviteeName : inviteeNames) {
            if (inviteeName.equals(getCurrentUser().getUserName())) {
                throw new BusinessException(BusinessExceptionType.OPERATION_NOT_PERMITTED);
            }
        }

        Long inviterId = getCurrentUser().getId();
        checkGameInitiatedByUser(gameId, inviterId);

        Game game = gameDao.findGameByIdAndUserId(gameId, getCurrentUser().getId());
        if (game == null || !isCurrentUserInitiator(game)) {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }

        int invitationsCount = invitationDao.countInvitationsByGameIdAndStatus(gameId, InvitationStatus.CREATED);
        int joinedCompetitorsCount = game.getCompetitors().size();
        GameVariantDto gv = gameVariantService.getGameVariantById(game.getGameVariantId());
        if (gv.getMaxGamersQuantity() < invitationsCount + joinedCompetitorsCount + inviteeNames.length) {
            throw new BusinessException(BusinessExceptionType.COMPETITORS_CAPACITY_EXHAUSTED);
        }

        for (String inviteeName : inviteeNames) {
            GamerAccount inviteeAccount = gamerAccountDao.findByUserName(inviteeName);
            if (inviteeAccount == null) {
                throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, inviteeName);
            }

            Invitation existedInvitation = invitationDao.findCreatedInvitationByGameIdAndInviteeName(gameId, inviteeName);
            if (existedInvitation != null) {
                throw new BusinessException(BusinessExceptionType.USER_ALREADY_INVITED, inviteeName);
            }

            Date currentDate = new Date();
            Date expiredDate = DateUtils.addMinutes(currentDate, invitationExpirationTimeout);
            Invitation invitation = new Invitation();
            invitation.setGameId(gameId);
            invitation.setCreatedTime(currentDate);
            invitation.setExpiredTime(expiredDate);
            invitation.setInviterId(inviterId);
            invitation.setInviteeId(inviteeAccount.getId());
            invitation.setStatus(CREATED);
            invitation.setInviteeAccount(inviteeAccount);
            invitation.setInviterAccount(gamerAccountDao.findByPrimaryKey(inviterId));

            invitationDao.create(invitation);

            if (!inviteeAccount.getIsBot()) {
                InvitationDto invitationDto = DtoMapper.map(invitation, InvitationDto.class);
                sendNotification(inviteeAccount.getId(), GameEventType.INVITATION_RECEIVED, invitationDto);
                invitationService.notifyByEmail(getCurrentUser().getUserName(), inviteeAccount, inviteeNames, game, gv);
            }
        }
    }

    private <T> void sendNotification(Long gamerId, GameEventType userNotificationType,
                                      T notificationBody) {
        UserNotification<T> notification =
                new UserNotification<>(null, userNotificationType, notificationBody);
        messagingService.send(gamerId, notification);
    }

    private boolean isCurrentUserInitiator(Game game) {
        Set<Competitor> competitors = game.getCompetitors();
        for (Competitor competitor : competitors) {
            if (competitor.getGamerId().equals(getCurrentUser().getId()) && competitor.getInitiator()) {
                return true;
            }
        }
        return false;
    }

    @DeniedForRemovedUser
    public void changeInvitationStatus(ChangeInvitationStatusDto changeInvitationStatusDto) {
        if (changeInvitationStatusDto.getGameSetId() != null) {
            List<Game> games = gameDao.findGamesByGameSeriesId(changeInvitationStatusDto.getGameSetId());
            changeInvitationStatusDto.setGameId(getGameIdFromGameSeries(games));
        }

        InvitationStatus newStatus = InvitationStatus.valueOf(changeInvitationStatusDto.getStatus());
        String[] userNames = InvitationStatus.CANCELLED.equals(newStatus)
                ? getInvitedUsers(changeInvitationStatusDto) : new String[]{getCurrentUser().getUserName()};

        for (String userName : userNames) {
            Invitation invitation =
                    invitationDao.findCreatedInvitationByGameIdAndInviteeName(changeInvitationStatusDto.getGameId(), userName);
            if (invitation != null) {
                if (!InvitationStatus.CREATED.equals(invitation.getStatus())
                        || InvitationStatus.CREATED.equals(newStatus)
                        || InvitationStatus.EXPIRED.equals(newStatus)
                        || InvitationStatus.ACCEPTED.equals(newStatus)) {
                    throw new BusinessException(BusinessExceptionType.ILLEGAL_INVITATION_STATUS,
                            changeInvitationStatusDto.getGameId(), userName);
                }

                changeInvitationStatus(invitation, newStatus);

                InvitationDto invitationDto = DtoMapper.map(invitation, InvitationDto.class);
                if (InvitationStatus.REJECTED.equals(newStatus)) {
                    GamerAccount recipient = invitation.getInviterAccount();
                    sendNotification(recipient.getId(), GameEventType.INVITATION_REJECTED, invitationDto);
                    if (!userSessionTrackingService.isUserOnline(recipient.getUserName())) {
                        mailPreparationService.prepareInvitationRejectedMessage(recipient.getUserName(), recipient.getEmail(),
                                getCurrentUser().getUserName(), recipient.getLocale());
                    }
                } else if (InvitationStatus.CANCELLED.equals(newStatus)) {
                    GamerAccount recipient = invitation.getInviteeAccount();
                    sendNotification(recipient.getId(), GameEventType.INVITATION_CANCELLED, invitationDto);
                    if (!userSessionTrackingService.isUserOnline(recipient.getUserName())) {
                        mailPreparationService.prepareInvitationCancelledMessage(recipient.getUserName(), recipient.getEmail(),
                                getCurrentUser().getUserName(), recipient.getLocale());
                    }
                }

                if (invitationDao.countInvitationsByGameIdAndStatus(changeInvitationStatusDto.getGameId(), CREATED) == 0) {
                    Game game = gameDao.findByPrimaryKey(changeInvitationStatusDto.getGameId());
                    if (game.getCompetitors().size() > 1) {
                        startGame(game, null);
                    }
                }
            } else {
                Game game = gameDao.findByPrimaryKey(changeInvitationStatusDto.getGameId());
                if (game != null) {
                    gameDao.remove(game);
                } else {
                    throw new BusinessException(BusinessExceptionType.INVITATION_NOT_FOUND, changeInvitationStatusDto.getGameId(), userName);
                }
            }
        }
    }

    private String[] getInvitedUsers(ChangeInvitationStatusDto changeInvitationStatusDto) {
        return ArrayUtils.isEmpty(changeInvitationStatusDto.getInviteeNames())
                ? getInvitedUsers(changeInvitationStatusDto.getGameId()) : changeInvitationStatusDto.getInviteeNames();
    }

    private String[] getInvitedUsers(Long gameId) {
        List<Invitation> invitations = invitationDao.findByGameId(gameId);
        return invitations.stream().map(Invitation::getInviteeAccount).map(GamerAccount::getUserName).toArray(String[]::new);
    }

    private void changeInvitationStatus(Invitation invitation, InvitationStatus status) {
        invitation.setStatus(status);
        invitation.setCompletedTime(new Date());
        invitationDao.update(invitation);
        if (status.equals(InvitationStatus.REJECTED) || status.equals(InvitationStatus.CANCELLED)) {
            if (invitationDao.countInvitationsByGameIdAndStatus(invitation.getGameId(), ACCEPTED) == 0
                    && invitationDao.countInvitationsByGameIdAndStatus(invitation.getGameId(), CREATED) == 0) {
                Game game = gameDao.findByPrimaryKey(invitation.getGameId());
                game.setGameStatus(GameStatus.CANCELLED);
                gameDao.update(game);
            }
        }
    }

    private void checkGameInitiatedByUser(Long gameId, Long gamerId) {
        if (!gameDao.isGameInitiatedByUser(gameId, gamerId)) {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }
    }

    private GameDto convertGameToDto(Game game, UserInfo userInfo) {
        GameDto gameDto = DtoMapper.map(game, GameDto.class);
        for (CompetitorDto competitorDto : gameDto.getCompetitors()) {
            if (!userInfo.getUserName().equals(competitorDto.getUserName())) {
                Iterator<CompetitorCardDto> iterator = competitorDto.getCompetitorCards().iterator();
                while (iterator.hasNext()) {
                    CompetitorCardDto competitorCard = iterator.next();
                    if (!competitorCard.getApplied()) {
                        iterator.remove();
                    }
                }
            }
        }
        return gameDto;
    }

    private Game findGameByIdAndUser(Long gameId, Long gamerId) {
        Game game = gameDao.findGameByIdAndUserId(gameId, gamerId);
        if (game == null) {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }
        return game;
    }

    private boolean isInitiator(Set<Competitor> competitors, Long gamerId) {
        for (Competitor competitor : competitors) {
            if (competitor.getGamerId().equals(gamerId) && competitor.getInitiator()) {
                return true;
            }
        }
        return false;
    }

    private void startGame(Game game, List<Long> predefinedGamersOrder) {
        Long variantId = game.getGameSeries().getGameVariantId();
        GameVariantDto gameVariantDto = gameVariantService.getGameVariantById(variantId);

        Date startedTime = new Date();

        game.getGameSeries().setCompetitorsQuantity(game.getCompetitors().size());
        game.setCompetitorsQuantity(game.getCompetitors().size());
        game.setStartedTime(startedTime);
        game.setGameStatus(RUNNING);

        GameSeries gameSeries = game.getGameSeries();
        gameSeries.setStartedTime(startedTime);

        if (predefinedGamersOrder == null || predefinedGamersOrder.isEmpty()) {
            generateMovesOrder(game.getCompetitors());
        } else {
            assignMoveOrder(game.getCompetitors(), predefinedGamersOrder);
        }
        generateCardSets(game, gameVariantDto);
        generateInitialAccountStates(game, gameVariantDto);

        game.setLabel(buildLabel(game, gameVariantDto.getMovesQuantity()));
    }

    private void assignMoveOrder(Set<Competitor> competitors, List<Long> predefinedGamersOrder) {
        for (int i = 0; i < predefinedGamersOrder.size(); i++) {
            Long gamerId = predefinedGamersOrder.get(i);
            for (Competitor competitor : competitors) {
                if (competitor.getGamerId().equals(gamerId)) {
                    competitor.setMoveOrder(i + 1);
                }
            }
        }
    }

    private String buildLabel(Game game, int movesQuantity) {
        StringBuilder sb = new StringBuilder();
        SortedSet<Competitor> competitorSortedSet = new TreeSet<>(game.getCompetitors());
        for (Competitor competitor : competitorSortedSet) {
            if (sb.length() > 0) {
                sb.append(" - ");
            }
            GamerAccount ga = gamerAccountDao.findByPrimaryKey(competitor.getGamerId());
            sb.append(ga.getUserName());
        }
        sb.append(" - ").append(movesQuantity);
        return sb.toString();
    }

    private void generateCardSets(Game game, GameVariantDto gameVariant) {
        Set<CardGroupDto> cardGroups = gameVariant.getCardGroups();
        Map<CardGroupDto, List<Long>> shuffledCards = new HashMap<>();
        for (CardGroupDto cardGroup : cardGroups) {
            List<Long> cardIds = shuffleCards(cardGroup);
            shuffledCards.put(cardGroup, cardIds);
        }
        distributeCards(game.getCompetitors(), shuffledCards);
    }

    private void distributeCards(Set<Competitor> competitors, Map<CardGroupDto, List<Long>> shuffledCards) {
        for (CardGroupDto cardGroup : shuffledCards.keySet()) {
            int gamerCardNumber = cardGroup.getGamerCardQuantity();
            List<Long> cards = shuffledCards.get(cardGroup);
            if (cards.size() < gamerCardNumber * competitors.size()) {
                throw new BusinessException(BusinessExceptionType.TOO_FEW_CARDS);
            }
            int j = 0;
            for (int i = 0; i < gamerCardNumber; i++) {
                for (Competitor competitor : competitors) {
                    if (competitor.getCompetitorCards() == null) {
                        competitor.setCompetitorCards(new HashSet<CompetitorCard>());
                    }
                    Long cardId = cards.get(j);
                    CompetitorCard cc = new CompetitorCard();
                    cc.setCardId(cardId);
                    cc.setCompetitor(competitor);
                    competitor.getCompetitorCards().add(cc);
                    j++;
                }
            }
        }
    }

    private List<Long> shuffleCards(CardGroupDto cardGroup) {
        List<Long> cardIds = new ArrayList<>();
        for (CardDto card : cardGroup.getCards()) {
            Long cardId = card.getId();
            for (int i = 0; i < card.getQuantity(); i++) {
                cardIds.add(cardId);
            }
        }
        return CollectionsUtil.shuffleObjects(cardIds, 5);
    }

    private void generateMovesOrder(Set<Competitor> competitors) {
        List<Competitor> shuffleCompetitors = CollectionsUtil.shuffleObjects(competitors, 3);
        for (int i = 0; i < shuffleCompetitors.size(); i++) {
            Competitor competitor = shuffleCompetitors.get(i);
            competitor.setMoveOrder(i + 1);
        }
    }

    private void generateInitialAccountStates(Game game, GameVariantDto gameVariant) {
        Move move = new Move();
        move.setMoveNumber(0);
        move.setGame(game);
        game.setMoves(CollectionsUtil.newSet(move));

        Set<GameShareDto> gameShares = gameVariant.getShares();
        Set<Competitor> competitors = game.getCompetitors();
        move.setCompetitorMoves(new HashSet<CompetitorMove>());

        for (Competitor competitor : competitors) {
            CompetitorMove competitorMove = new CompetitorMove();
            competitorMove.setMoveOrder(competitor.getMoveOrder());
            competitorMove.setFinishedTime(game.getStartedTime());
            competitorMove.setCompetitor(competitor);
            competitorMove.setMove(move);
            move.getCompetitorMoves().add(competitorMove);

            MoveStep step = new MoveStep();
            step.setCompetitorMove(competitorMove);
            step.setStepType(ZERO_STEP);
            step.setCashValue(gameVariant.getGamerInitialCash());

            competitorMove.setSteps(CollectionsUtil.newList(step));

            Set<SharePrice> sharePrices = new HashSet<>();
            Set<ShareQuantity> shareQuantities = new HashSet<>();
            for (GameShareDto gameShare : gameShares) {
                Long shareId = gameShare.getId();

                if (competitor.getMoveOrder().equals(game.getCompetitorsQuantity())) {
                    SharePrice sharePrice = new SharePrice();
                    SharePricePk spk = new SharePricePk();
                    spk.setShareId(shareId);
                    sharePrice.setId(spk);
                    sharePrice.setPrice(gameShare.getInitPrice());
                    sharePrice.setStep(step);
                    sharePrices.add(sharePrice);
                }

                ShareQuantity shareQuantity = new ShareQuantity();
                ShareQuantityPk qpk = new ShareQuantityPk();
                qpk.setShareId(shareId);
                shareQuantity.setId(qpk);
                shareQuantity.setQuantity(gameShare.getInitQuantity());
                shareQuantity.setStep(step);
                shareQuantities.add(shareQuantity);
            }
            step.setShareQuantities(shareQuantities);
            step.setSharePrices(sharePrices);
        }
    }
}
