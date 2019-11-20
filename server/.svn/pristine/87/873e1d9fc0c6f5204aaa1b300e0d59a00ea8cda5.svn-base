package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.model.MoveData;
import com.stockholdergame.server.gamecore.model.result.MoveResult;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 23.38
 */
public interface MovePerformer {

    MoveResult doMove(MoveData moveData, GameState gameState) throws NotEnoughSharesException, SharesLockedException,
        GameIsFinishedException, ShareNotFoundException,
        NotEnoughFundsException, SharePriceAlreadyChangedException;

    void addBeforeCommitListener(BeforeCommitListener listener);

    void removeBeforeCommitListener(BeforeCommitListener listener);
}
