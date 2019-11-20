package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.ScoreDao;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.game.archive.Score;
import com.stockholdergame.server.model.game.archive.ScorePk;
import com.stockholdergame.server.services.game.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aliaksandr Savin
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    @Override
    public void updateScore(Long firstGamerId,
                            Long secondGamerId,
                            Long gameVariantId,
                            int firstGamerMoveOrder,
                            boolean isFirstGamerWinner,
                            boolean isFirstGamerBankrupt,
                            String rulesVersion) {
        if (isFirstGamerWinner && isFirstGamerBankrupt) {
            throw new ApplicationException("Gamer can't be winner and bankrupt at one time");
        }

        ScorePk scorePk = new ScorePk(firstGamerId, secondGamerId, gameVariantId, firstGamerMoveOrder, rulesVersion);
        Score score = scoreDao.findByPrimaryKey(scorePk);
        if (score == null) {
            score = new Score();
            score.setId(scorePk);
            score.setWinningsCount(isFirstGamerWinner ? 1 : 0);
            score.setDefeatsCount(isFirstGamerWinner ? 0 : 1);
            score.setBankruptsCount(isFirstGamerBankrupt ? 1 : 0);
            scoreDao.create(score);
        } else {
            int winningsCount = score.getWinningsCount();
            int defeatsCount = score.getDefeatsCount();
            int bankruptsCount = score.getBankruptsCount();
            if (isFirstGamerWinner) {
                winningsCount++;
            } else {
                defeatsCount++;
            }
            if (isFirstGamerBankrupt) {
                bankruptsCount++;
            }
            score.setWinningsCount(winningsCount);
            score.setDefeatsCount(defeatsCount);
            score.setBankruptsCount(bankruptsCount);
            scoreDao.update(score);
        }
    }
}
