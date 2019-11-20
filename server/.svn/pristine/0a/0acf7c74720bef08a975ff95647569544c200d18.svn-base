package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dto.game.BuySellDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.PriceOperationDto;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.*;
import com.stockholdergame.server.model.game.variant.*;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import java.util.*;

import static com.stockholdergame.server.model.game.GameStatus.RUNNING;
import static com.stockholdergame.server.model.game.StepType.ZERO_STEP;
import static com.stockholdergame.server.model.game.variant.Rounding.U;

/**
 * @author Alexander Savin
 */
public class GameServiceDataProvider {

    public Object[][] createGameVariantData() {
        return new Object[][]{
                {1L, "name", 100, 4, 10, U, 250, 10,
                        createShareSet(),
                        new Object[][]{
                                createBigCardsGroup(),
                                createSmallCardsGroup()
                        }
                }
        };
    }


    private Object[] createBigCardsGroup() {
        return new Object[]{1L, "big", 4,
                new Object[][]{
                        {1L, 3,
                                new Object[][]{
                                        {1L, PriceOperationType.ADDITION, 100, 1L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L},
                                        {null, PriceOperationType.SUBTRACTION, 20, 10L},
                                        {null, PriceOperationType.SUBTRACTION, 10, 11L}
                                }
                        },
                        {2L, 3,
                                new Object[][]{
                                        {2L, PriceOperationType.ADDITION, 100, 1L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L},
                                        {null, PriceOperationType.SUBTRACTION, 20, 10L},
                                        {null, PriceOperationType.SUBTRACTION, 10, 11L}
                                }
                        },
                        {3L, 3,
                                new Object[][]{
                                        {3L, PriceOperationType.ADDITION, 100, 1L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L},
                                        {null, PriceOperationType.SUBTRACTION, 20, 10L},
                                        {null, PriceOperationType.SUBTRACTION, 10, 11L}
                                }
                        },
                        {4L, 3,
                                new Object[][]{
                                        {4L, PriceOperationType.ADDITION, 100, 1L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L},
                                        {null, PriceOperationType.SUBTRACTION, 20, 10L},
                                        {null, PriceOperationType.SUBTRACTION, 10, 11L}
                                }
                        },
                        {5L, 1,
                                new Object[][]{
                                        {1L, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {null, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {6L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {1L, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {7L, 1,
                                new Object[][]{
                                        {2L, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {null, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {8L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {2L, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {9L, 1,
                                new Object[][]{
                                        {3L, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {null, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {10L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {3L, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {11L, 1,
                                new Object[][]{
                                        {4L, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {null, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                        {12L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.MULTIPLICATION, 2, 12L},
                                        {4L, PriceOperationType.DIVISION, 2, 13L}
                                }
                        },
                }
        };
    }

    private Object[] createSmallCardsGroup() {
        return new Object[]{2L, "small", 6,
                new Object[][]{
                        {13L, 1,
                                new Object[][]{
                                        {1L, PriceOperationType.ADDITION, 30, 5L},
                                        {null, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {14L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 30, 5L},
                                        {1L, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {15L, 1,
                                new Object[][]{
                                        {2L, PriceOperationType.ADDITION, 30, 5L},
                                        {null, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {16L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 30, 5L},
                                        {2L, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {17L, 1,
                                new Object[][]{
                                        {3L, PriceOperationType.ADDITION, 30, 5L},
                                        {null, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {18L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 30, 5L},
                                        {3L, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {19L, 1,
                                new Object[][]{
                                        {4L, PriceOperationType.ADDITION, 30, 5L},
                                        {null, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {20L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 30, 5L},
                                        {4L, PriceOperationType.SUBTRACTION, 60, 6L}
                                }
                        },
                        {21L, 1,
                                new Object[][]{
                                        {1L, PriceOperationType.ADDITION, 40, 4L},
                                        {null, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {22L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 40, 4L},
                                        {1L, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {23L, 1,
                                new Object[][]{
                                        {2L, PriceOperationType.ADDITION, 40, 4L},
                                        {null, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {24L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 40, 4L},
                                        {2L, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {25L, 1,
                                new Object[][]{
                                        {3L, PriceOperationType.ADDITION, 40, 4L},
                                        {null, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {26L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 40, 4L},
                                        {3L, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {27L, 1,
                                new Object[][]{
                                        {4L, PriceOperationType.ADDITION, 40, 4L},
                                        {null, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {28L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 40, 4L},
                                        {4L, PriceOperationType.SUBTRACTION, 50, 7L}
                                }
                        },
                        {29L, 1,
                                new Object[][]{
                                        {1L, PriceOperationType.ADDITION, 50, 3L},
                                        {null, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {30L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 50, 3L},
                                        {1L, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {31L, 1,
                                new Object[][]{
                                        {2L, PriceOperationType.ADDITION, 50, 3L},
                                        {null, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {32L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 50, 3L},
                                        {2L, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {33L, 1,
                                new Object[][]{
                                        {3L, PriceOperationType.ADDITION, 50, 3L},
                                        {null, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {34L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 50, 3L},
                                        {3L, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {35L, 1,
                                new Object[][]{
                                        {4L, PriceOperationType.ADDITION, 50, 3L},
                                        {null, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {36L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 50, 3L},
                                        {4L, PriceOperationType.SUBTRACTION, 40, 8L}
                                }
                        },
                        {37L, 1,
                                new Object[][]{
                                        {1L, PriceOperationType.ADDITION, 60, 2L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {38L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 60, 2L},
                                        {1L, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {39L, 1,
                                new Object[][]{
                                        {2L, PriceOperationType.ADDITION, 60, 2L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {40L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 60, 2L},
                                        {2L, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {41L, 1,
                                new Object[][]{
                                        {3L, PriceOperationType.ADDITION, 60, 2L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {42L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 60, 2L},
                                        {3L, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {43L, 1,
                                new Object[][]{
                                        {4L, PriceOperationType.ADDITION, 60, 2L},
                                        {null, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                        {44L, 1,
                                new Object[][]{
                                        {null, PriceOperationType.ADDITION, 60, 2L},
                                        {4L, PriceOperationType.SUBTRACTION, 30, 9L}
                                }
                        },
                }
        };
    }

    private Object[][] createShareSet() {
        return new Object[][]{
                {1L, 100, 1, Color.YELLOW},
                {2L, 100, 1, Color.RED},
                {3L, 100, 1, Color.BLUE},
                {4L, 100, 1, Color.GREEN}
        };
    }

    public List<GameVariant> createGameVariants() {
        List<GameVariant> gameVariants = CollectionsUtil.newList();
        for (Object[] objects : createGameVariantData()) {
            gameVariants.add(createGameVariant(objects));
        }
        return gameVariants;
    }

    public GameVariant createGameVariant() {
        return createGameVariant(createGameVariantData()[0]);
    }

    public GameVariant createGameVariant(Object[] data) {
        GameVariant gameVariant = new GameVariant();
        gameVariant.setId((Long) data[0]);
        gameVariant.setName((String) data[1]);
        gameVariant.setGamerInitialCash((Integer) data[2]);
        gameVariant.setMaxGamersQuantity((Integer) data[3]);
        gameVariant.setMovesQuantity((Integer) data[4]);
        gameVariant.setRounding((Rounding) data[5]);

        PriceScale priceScale = new PriceScale();
        priceScale.setMaxValue((Integer) data[6]);
        priceScale.setScaleSpacing((Integer) data[7]);
        gameVariant.setPriceScale(priceScale);

        Set<GameShare> gameShares = new HashSet<GameShare>();
        for (Object[] o : (Object[][]) data[8]) {
            GameShare gs = new GameShare();
            GameSharePk gsk = new GameSharePk();
            gsk.setShareId((Long) o[0]);
            gs.setId(gsk);
            gs.setInitPrice((Integer) o[1]);
            gs.setInitQuantity((Integer) o[2]);
            gs.setColor((Color) o[3]);
            gameShares.add(gs);
        }
        gameVariant.setShares(gameShares);

        Set<GameCardGroup> gameCardGroups = new HashSet<GameCardGroup>();
        for (Object[] o : (Object[][]) data[9]) {
            CardGroup cardGroup = new CardGroup();
            cardGroup.setId((Long) o[0]);
            cardGroup.setGroupName((String) o[1]);

            GameCardGroup gameCardGroup = new GameCardGroup();
            GameCardGroupPk gameCardGroupPk = new GameCardGroupPk();
            gameCardGroupPk.setCardGroupId((Long) o[0]);
            gameCardGroup.setId(gameCardGroupPk);
            gameCardGroup.setGamerCardQuantity((Integer) o[2]);
            gameCardGroup.setCardGroup(cardGroup);
            gameCardGroups.add(gameCardGroup);

            Set<CardQuantity> cardQuantities = new HashSet<CardQuantity>();
            for (Object[] o1 : (Object[][]) o[3]) {
                Card card = new Card();
                card.setId((Long) o1[0]);

                CardQuantity cardQuantity = new CardQuantity();
                cardQuantity.setQuantity((Integer) o1[1]);
                cardQuantity.setCard(card);
                cardQuantity.setCardGroup(cardGroup);
                cardQuantities.add(cardQuantity);

                Set<CardOperation> cardOperations = new HashSet<CardOperation>();
                for (Object[] o2 : (Object[][]) o1[2]) {
                    CardOperation cardOperation = new CardOperation();
                    cardOperation.setShareId((Long) o2[0]);
                    PriceOperation priceOperation = new PriceOperation();
                    priceOperation.setId((Long) o2[3]);
                    priceOperation.setOperandValue((Integer) o2[2]);
                    priceOperation.setPriceOperationType((PriceOperationType) o2[1]);
                    cardOperation.setPriceOperation(priceOperation);
                    cardOperations.add(cardOperation);
                }
                card.setCardOperations(cardOperations);
            }
            cardGroup.setCardQuantities(cardQuantities);
        }
        gameVariant.setCardGroups(gameCardGroups);

        return gameVariant;
    }

    public Game createGameData() {
        Date current = new Date();

        Game game = createGame(1L, 1L, 2, current, RUNNING, 250, 10, current);

        Competitor competitor1 = createCompetitor(1L, 1L, 1, true, current);
        Competitor competitor2 = createCompetitor(2L, 2L, 2, false, current);
        competitor1.setGamerAccount(createGamerAccount());
        competitor2.setGamerAccount(createGamerAccount());
        linkCompetitorsWithGame(game, competitor1, competitor2);

        createCardsSet(competitor1, 26L, 17L, 9L, 21L, 2L, 13L, 4L, 4L, 25L, 22L);
        createCardsSet(competitor2, 14L, 36L, 6L, 4L, 1L, 35L, 5L, 30L, 23L, 19L);

        Move move0 = createMove(10L, 0);
        linkMoveWithGame(game, move0);

        CompetitorMove competitorMove10 = createCompetitorMove(10L, competitor1, current);
        linkCompetitorMovesWithMove(move0, competitorMove10);

        MoveStep step100 = createMoveStep(100L, ZERO_STEP, 0);
        linkMoveStepsWithMove(competitorMove10, step100);

        ShareQuantity shareQuantity1001 = createShareQuantity(1L, 100L, 1);
        ShareQuantity shareQuantity1002 = createShareQuantity(2L, 100L, 1);
        ShareQuantity shareQuantity1003 = createShareQuantity(3L, 100L, 1);
        ShareQuantity shareQuantity1004 = createShareQuantity(4L, 100L, 1);
        linkShareQuantitiesWithMoveStep(step100, shareQuantity1001, shareQuantity1002, shareQuantity1003,
                shareQuantity1004);

        CompetitorMove competitorMove20 = createCompetitorMove(20L, competitor2, current);
        linkCompetitorMovesWithMove(move0, competitorMove20);

        MoveStep step200 = createMoveStep(200L, ZERO_STEP, 0);
        linkMoveStepsWithMove(competitorMove20, step200);

        SharePrice sharePrice201 = createSharePrice(1L, 200L, 100);
        SharePrice sharePrice202 = createSharePrice(2L, 200L, 100);
        SharePrice sharePrice203 = createSharePrice(3L, 200L, 100);
        SharePrice sharePrice204 = createSharePrice(4L, 200L, 100);
        linkSharePricesWithStep(step200, sharePrice201, sharePrice202, sharePrice203, sharePrice204);

        ShareQuantity shareQuantity2001 = createShareQuantity(1L, 200L, 1);
        ShareQuantity shareQuantity2002 = createShareQuantity(2L, 200L, 1);
        ShareQuantity shareQuantity2003 = createShareQuantity(3L, 200L, 1);
        ShareQuantity shareQuantity2004 = createShareQuantity(4L, 200L, 1);
        linkShareQuantitiesWithMoveStep(step200, shareQuantity2001, shareQuantity2002, shareQuantity2003,
                shareQuantity2004);

        return game;
    }

    private void linkMoveWithGame(Game game, Move... moves) {
        for (Move move : moves) {
            move.setGame(game);
            if (game.getMoves() == null) {
                game.setMoves(CollectionsUtil.newSet(move));
            } else {
                game.getMoves().add(move);
            }
        }
    }

    private Move createMove(long moveId, int moveNumber) {
        Move move = new Move();
        move.setId(moveId);
        move.setMoveNumber(moveNumber);
        return move;
    }

    private void createCardsSet(Competitor competitor, Long... cardIds) {
        for (Long cardId : cardIds) {
            CompetitorCard competitorCard = new CompetitorCard();
            competitorCard.setId(competitor.getId() * 1000 + cardId);
            competitorCard.setCardId(cardId);
            competitorCard.setCompetitor(competitor);
            if (competitor.getCompetitorCards() == null) {
                competitor.setCompetitorCards(CollectionsUtil.newSet(competitorCard));
            } else {
                competitor.getCompetitorCards().add(competitorCard);
            }
        }
    }

    private ShareQuantity createShareQuantity(long shareId, long stepId, int quantity) {
        ShareQuantity shareQuantity = new ShareQuantity();
        ShareQuantityPk pk = new ShareQuantityPk();
        pk.setShareId(shareId);
        pk.setStepId(stepId);
        shareQuantity.setId(pk);
        shareQuantity.setQuantity(quantity);
        return shareQuantity;
    }

    private void linkShareQuantitiesWithMoveStep(MoveStep step, ShareQuantity... shareQuantities) {
        for (ShareQuantity shareQuantity : shareQuantities) {
            shareQuantity.setStep(step);
            if (step.getShareQuantities() == null) {
                step.setShareQuantities(CollectionsUtil.newSet(shareQuantity));
            } else {
                step.getShareQuantities().add(shareQuantity);
            }
        }
    }

    private MoveStep createMoveStep(long stepId, StepType stepType, int cash) {
        MoveStep step = new MoveStep();
        step.setId(stepId);
        step.setStepType(stepType);
        step.setCashValue(cash);
        return step;
    }

    private void linkMoveStepsWithMove(CompetitorMove competitorMove, MoveStep... steps) {
        for (MoveStep step : steps) {
            step.setCompetitorMove(competitorMove);
            if (competitorMove.getSteps() == null) {
                competitorMove.setSteps(CollectionsUtil.newList(step));
            } else {
                competitorMove.getSteps().add(step);
            }
        }
    }

    private SharePrice createSharePrice(long shareId, long stepId, int price) {
        SharePrice sharePrice = new SharePrice();
        SharePricePk pk = new SharePricePk();
        pk.setShareId(shareId);
        pk.setStepId(stepId);
        sharePrice.setId(pk);
        sharePrice.setPrice(price);
        return sharePrice;
    }

    private void linkSharePricesWithStep(MoveStep step, SharePrice... sharePrices) {
        for (SharePrice sharePrice : sharePrices) {
            sharePrice.setStep(step);
            if (step.getSharePrices() == null) {
                step.setSharePrices(CollectionsUtil.newSet(sharePrice));
            } else {
                step.getSharePrices().add(sharePrice);
            }
        }
    }

    private CompetitorMove createCompetitorMove(long moveId, Competitor competitor, Date finishTime) {
        CompetitorMove competitorMove = new CompetitorMove();
        competitorMove.setId(moveId);
        competitorMove.setFinishedTime(finishTime);
        competitorMove.setMoveOrder(competitor.getMoveOrder());
        competitorMove.setCompetitor(competitor);
        return competitorMove;
    }

    private void linkCompetitorMovesWithMove(Move move, CompetitorMove... competitorMoves) {
        for (CompetitorMove competitorMove : competitorMoves) {
            competitorMove.setMove(move);
            if (move.getCompetitorMoves() == null) {
                move.setCompetitorMoves(CollectionsUtil.newSet(competitorMove));
            } else {
                move.getCompetitorMoves().add(competitorMove);
            }
        }
    }

    private Competitor createCompetitor(long competitorId, long gamerId, int moveOrder, boolean isInitiator,
                                        Date joinTime) {
        Competitor competitor = new Competitor();
        competitor.setId(competitorId);
        competitor.setGamerId(gamerId);
        competitor.setMoveOrder(moveOrder);
        competitor.setInitiator(isInitiator);
        competitor.setJoinedTime(joinTime);
        return competitor;
    }

    private void linkCompetitorsWithGame(Game game, Competitor... competitors) {
        for (Competitor competitor : competitors) {
            competitor.setGame(game);
            if (game.getCompetitors() == null) {
                game.setCompetitors(CollectionsUtil.newSet(competitor));
            } else {
                game.getCompetitors().add(competitor);
            }
        }
    }

    private Game createGame(long id, long gameVariantId, int competitorsQuantity, Date createdTime, GameStatus status,
                            int maxSharePrice, int sharePriceStep, Date startedTime) {
        Game game = new Game();
        game.setId(id);
        game.setGameVariantId(gameVariantId);
        game.setCompetitorsQuantity(competitorsQuantity);
        game.setCreatedTime(createdTime);
        game.setGameStatus(status);
        game.setMaxSharePrice(maxSharePrice);
        game.setSharePriceStep(sharePriceStep);
        game.setStartedTime(startedTime);
        return game;
    }

    public List<DoMoveDto> createMoveData() {
        List<DoMoveDto> moves = new ArrayList<DoMoveDto>();

        DoMoveDto move11 = createMove11();

        moves.add(move11);
        return moves;
    }

    private Set<PriceOperationDto> createPriceOperations(Object[][] objects) {
        Set<PriceOperationDto> priceOperations = new HashSet<PriceOperationDto>();
        for (Object[] object : objects) {
            PriceOperationDto priceOperation = new PriceOperationDto();
            priceOperation.setShareId((Long) object[0]);
            priceOperation.setPriceOperationId((Long) object[1]);
            priceOperations.add(priceOperation);
        }
        return priceOperations;
    }

    private Set<BuySellDto> createActions(Object[][] objects) {
        Set<BuySellDto> buySellDtos = new HashSet<BuySellDto>();
        for (Object[] object : objects) {
            BuySellDto buySellDto = new BuySellDto();
            buySellDto.setShareId((Long) object[0]);
            buySellDto.setBuySellQuantity((Integer) object[1]);
            buySellDtos.add(buySellDto);
        }
        return buySellDtos;
    }

    private DoMoveDto createMove11() {
        return createMoveData(1L, 26L, new Object[][]{{2L, -1}, {3L, -1}, {4L, 1}},
                new Object[][] {{4L, 4L}}, new Object[][]{{3L, 4}, {4L, -1}});
    }

    private DoMoveDto createMoveData(Long gameId, Long appliedCardId,
                                     Object[][] actionsBefore, Object[][] priceOperations, Object[][] actionsAfter) {
        DoMoveDto move = new DoMoveDto();
        move.setGameId(gameId);
        move.setAppliedCardId(appliedCardId);
        move.setFirstBuySellActions(createActions(actionsBefore));
        move.setPriceOperations(createPriceOperations(priceOperations));
        move.setLastBuySellActions(createActions(actionsAfter));
        return move;
    }

    private GamerAccount createGamerAccount() {
        GamerAccount ga = new GamerAccount();
        ga.setUserName("user");
        ga.setSubtopicName("subtopic");
        ga.setIsBot(false);
        return ga;
    }
}
