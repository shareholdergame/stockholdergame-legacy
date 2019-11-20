package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.game.variant.Rounding;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 9.3.12 18.47
 */
public final class GameDataProvider {

    public static final Game FINISHED_GAME = createFinishedGame();

    public static Move createMove1(Game game) {
        Set<Competitor> competitors = game.getCompetitors();
        Competitor c1 = null, c2 = null;
        CompetitorCard cc1 = null, cc2 = null;
        for (Competitor competitor : competitors) {
            if (competitor.getMoveOrder() == 1) {
                c2 = competitor;
                cc2 = competitor.getCompetitorCards().iterator().next();
            } else {
                c1 = competitor;
                cc1 = competitor.getCompetitorCards().iterator().next();
            }
        }

        Date current = new Date();

        // -- move 1 ---
        Move m1 = new Move();
        m1.setMoveNumber(1);
        m1.setGame(game);

        CompetitorMove cm11 = new CompetitorMove();
        cm11.setMove(m1);
        cm11.setCompetitor(c2);
        cm11.setMoveOrder(1);
        cm11.setFinishedTime(current);
        cm11.setAppliedCardId(cc2.getId());

        CompetitorMove cm12 = new CompetitorMove();
        cm12.setMove(m1);
        cm12.setCompetitor(c1);
        cm12.setMoveOrder(2);
        cm12.setFinishedTime(current);
        cm11.setAppliedCardId(cc1.getId());

        m1.setCompetitorMoves(new HashSet<CompetitorMove>(Arrays.asList(cm11, cm12)));

        return m1;
    }

    private static Game createFinishedGame() {
        Date current = new Date();

        Game game = new Game();
        game.setGameVariantId(2L);
        game.setMaxSharePrice(250);
        game.setSharePriceStep(10);
        game.setRounding(Rounding.U);
        game.setCompetitorsQuantity(2);
        game.setGameStatus(GameStatus.FINISHED);
        game.setCreatedTime(current);
        game.setStartedTime(current);
        game.setFinishedTime(current);
        game.setRulesVersion(GameRulesVersion.RULES_1_3);
        game.setGameLetter("A");

        Competitor c1 = new Competitor();
        c1.setGame(game);
        c1.setGamerId(1L);
        c1.setMoveOrder(2);
        c1.setJoinedTime(current);
        c1.setInitiator(true);
        c1.setWinner(true);
        c1.setTotalFunds(6440);

        Competitor c2 = new Competitor();
        c2.setGame(game);
        c2.setGamerId(3L);
        c2.setMoveOrder(1);
        c2.setInitiator(false);
        c2.setJoinedTime(current);
        c2.setTotalFunds(5910);

        CompetitorCard cc11 = new CompetitorCard();
        cc11.setCompetitor(c1);
        cc11.setCardId(1L);
        cc11.setApplied(true);

        CompetitorCard cc21 = new CompetitorCard();
        cc21.setCompetitor(c2);
        cc21.setCardId(9L);
        cc21.setApplied(true);

        c1.setCompetitorCards(new HashSet<CompetitorCard>(Arrays.asList(cc11)));
        c2.setCompetitorCards(new HashSet<CompetitorCard>(Arrays.asList(cc21)));

        game.setCompetitors(new HashSet<Competitor>(Arrays.asList(c1, c2)));

        // -- Zero move --
        Move zm = new Move();
        zm.setMoveNumber(0);
        zm.setGame(game);
        game.setMoves(new HashSet<Move>(Arrays.asList(zm)));

        CompetitorMove cm01 = new CompetitorMove();
        cm01.setMove(zm);
        cm01.setCompetitor(c2);
        cm01.setMoveOrder(1);
        cm01.setFinishedTime(current);

        CompetitorMove cm02 = new CompetitorMove();
        cm02.setMove(zm);
        cm02.setCompetitor(c1);
        cm02.setMoveOrder(2);
        cm02.setFinishedTime(current);

        zm.setCompetitorMoves(new HashSet<CompetitorMove>(Arrays.asList(cm01, cm02)));

        MoveStep zms1 = new MoveStep();
        zms1.setCompetitorMove(cm01);
        zms1.setStepType(StepType.ZERO_STEP);
        zms1.setCashValue(0);

        MoveStep zms2 = new MoveStep();
        zms2.setCompetitorMove(cm02);
        zms2.setStepType(StepType.ZERO_STEP);
        zms2.setCashValue(0);

        cm01.setSteps(Arrays.asList(zms1));
        cm02.setSteps(Arrays.asList(zms2));

        return game;
    }

    private GameDataProvider() {
    }
}
