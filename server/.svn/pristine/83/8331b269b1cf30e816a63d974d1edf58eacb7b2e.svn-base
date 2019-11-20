package com.stockholdergame.server.util.game;

import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.MOVE_NOT_ENDED_PROPERLY;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;
import static com.stockholdergame.server.model.game.StepType.PRICE_CHANGE_STEP;
import static com.stockholdergame.server.model.game.StepType.ZERO_STEP;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.builders.CompetitorAccountBuilder;
import com.stockholdergame.server.gamecore.builders.GameStateBuilder;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.CompetitorAccountExtraData;
import com.stockholdergame.server.model.game.CompetitorCard;
import com.stockholdergame.server.model.game.CompetitorMove;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameStateExtraData;
import com.stockholdergame.server.model.game.Move;
import com.stockholdergame.server.model.game.MoveStep;
import com.stockholdergame.server.model.game.SharePrice;
import com.stockholdergame.server.model.game.ShareQuantity;
import com.stockholdergame.server.model.game.variant.Rounding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Game state creator.
 */
public final class GameStateCreator {

    private GameStateCreator() {
    }

    public static GameState createGameState(Game game) {
        GameStateExtraData extraData = new GameStateExtraData();
        extraData.setGameId(game.getId());
        extraData.setGameVariantId(game.getGameVariantId());
        extraData.setGameStatus(game.getGameStatus());

        TreeSet<Competitor> sortedCompetitors = new TreeSet<>(game.getCompetitors());

        TreeSet<Move> sortedMoves = new TreeSet<>(game.getMoves());
        Move lastMove = sortedMoves.last();
        Move prevMove = getPreviousMove(sortedMoves);
        TreeSet<CompetitorMove> sortedCompetitorMoves = new TreeSet<>(lastMove.getCompetitorMoves());
        CompetitorMove lastCompetitorMove = sortedCompetitorMoves.last();

        GameStateBuilder<GameStateExtraData> builder = GameStateBuilder.newBuilder(game.getGameVariant().getMovesQuantity(),
                lastMove.getMoveNumber(), lastCompetitorMove.getMoveOrder(), game.getSharePriceStep(),
                game.getMaxSharePrice(), Rounding.D.equals(game.getRounding()));
        TreeSet<MoveStep> moveSteps = new TreeSet<>(lastCompetitorMove.getSteps());
        MoveStep priceChangeStep = findPriceStep(moveSteps);
        for (SharePrice sharePrice : priceChangeStep.getSharePrices()) {
            builder.addShare(sharePrice.getId().getShareId(), sharePrice.getPrice());
        }
        Set<Long> gamerIds = new HashSet<>();
        for (Competitor competitor : sortedCompetitors) {
            gamerIds.add(competitor.getGamerId());

            CompetitorMove lcm = getLastCompetitorMove(competitor.getId(), lastMove, prevMove);
            // todo - last move can be null
            CompetitorAccountBuilder<CompetitorAccountExtraData> competitorAccountBuilder = builder.addCompetitor(competitor.getMoveOrder(),
                    competitor.getOut(), lcm != null ? findLastStep(lcm).getCashValue() : 0);  // todo - lcm != null ? - refactor this
            if (lcm != null) {
                MoveStep lastStep = findLastStepWithShareQuantities(lcm);
                for (ShareQuantity shareQuantity : lastStep.getShareQuantities()) {
                    competitorAccountBuilder.addShareQuantity(shareQuantity.getId().getShareId(), shareQuantity.getQuantity());
                }
            }

            CompetitorAccountExtraData accountExtraData = new CompetitorAccountExtraData();
            accountExtraData.setGamerId(competitor.getGamerId());
            accountExtraData.setUserName(competitor.getGamerAccount().getUserName());
            accountExtraData.setCompetitorId(competitor.getId());
            accountExtraData.setSubtopicName(competitor.getGamerAccount().getSubtopicName());
            accountExtraData.setBot(competitor.getGamerAccount().getIsBot());
            processCards(accountExtraData, competitor.getCompetitorCards());
            competitorAccountBuilder.setExtraData(accountExtraData);
            competitorAccountBuilder.finishBuilding();
        }
        extraData.setGamerIds(gamerIds);
        builder.setExtraData(extraData);

        return builder.getGameState();
    }

    private static Move getPreviousMove(TreeSet<Move> sortedMoves) {
        int i = 0;
        for (Move move : sortedMoves) {
            if (i == sortedMoves.size() - 2) {
                return move;
            }
            i++;
        }
        return null;
    }

    private static MoveStep findPriceStep(Set<MoveStep> moveSteps) {
        for (MoveStep moveStep : moveSteps) {
            if (moveStep.getStepType().equals(PRICE_CHANGE_STEP) || moveStep.getStepType().equals(ZERO_STEP)) {
                return moveStep;
            }
        }
        throw new ApplicationException(getMessage(MOVE_NOT_ENDED_PROPERLY));
    }

    private static CompetitorMove getLastCompetitorMove(Long id, Move lastMove, Move prevMove) {
        CompetitorMove lastCompetitorMove = null;
        for (CompetitorMove competitorMove : lastMove.getCompetitorMoves()) {
            if (competitorMove.getCompetitor().getId().equals(id)) {
                lastCompetitorMove = competitorMove;
            }
        }
        if (lastCompetitorMove == null) {
            if (prevMove != null) {
                for (CompetitorMove competitorMove : prevMove.getCompetitorMoves()) {
                    if (competitorMove.getCompetitor().getId().equals(id)) {
                        lastCompetitorMove = competitorMove;
                    }
                }
            } else {
                throw new ApplicationException("Can't find last competitor move. Something wrong in objects structure");
            }
        }
        return lastCompetitorMove;
    }

    private static MoveStep findLastStepWithShareQuantities(CompetitorMove competitorMove) {
        TreeSet<MoveStep> moveSteps = new TreeSet<>(competitorMove.getSteps());
        MoveStep found = null;
        for (MoveStep moveStep : moveSteps) {
            if (moveStep.getShareQuantities() != null && moveStep.getShareQuantities().size() > 0) {
                found = moveStep;
            }
        }
        if (moveSteps.isEmpty() || found == null) {
            throw new ApplicationException(getMessage(MOVE_NOT_ENDED_PROPERLY));
        }
        return found;
    }

    private static MoveStep findLastStep(CompetitorMove competitorMove) {
        TreeSet<MoveStep> moveSteps = new TreeSet<>(competitorMove.getSteps());
        if (moveSteps.isEmpty()) {
            throw new ApplicationException(getMessage(MOVE_NOT_ENDED_PROPERLY));
        }
        return moveSteps.last();
    }

    private static void processCards(CompetitorAccountExtraData accountExtraData, Set<CompetitorCard> competitorCards) {
        Map<Long, Long> availableCardsMap = new HashMap<>();
        for (CompetitorCard competitorCard : competitorCards) {
            if (!competitorCard.getApplied()) {
                availableCardsMap.put(competitorCard.getId(), competitorCard.getCardId());
            }
        }
        accountExtraData.setAvailableCardsMap(availableCardsMap);
    }
}
