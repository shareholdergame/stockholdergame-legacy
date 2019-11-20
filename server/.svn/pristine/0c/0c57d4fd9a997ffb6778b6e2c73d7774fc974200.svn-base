package com.stockholdergame.server.model.game.archive;

import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.variant.GameVariant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Alexander Savin
 *         Date: 22.1.13 22.34
 */
@Entity
@Table(name = "fg_scores")
public class Score implements Serializable {

    private static final long serialVersionUID = -2557931209360486137L;

    @Id
    private ScorePk id;

    @Column(name = "winnings_count", nullable = false)
    private Integer winningsCount;

    @Column(name = "defeats_count", nullable = false)
    private Integer defeatsCount;

    @Column(name = "bankrupts_count", nullable = false)
    private Integer bankruptsCount;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "second_gamer_id", insertable = false, updatable = false)
    private GamerAccount secondGamerAccount;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "game_variant_id", insertable = false, updatable = false)
    private GameVariant gameVariant;

    public ScorePk getId() {
        return id;
    }

    public void setId(ScorePk id) {
        this.id = id;
    }

    public Integer getWinningsCount() {
        return winningsCount;
    }

    public void setWinningsCount(Integer winningsCount) {
        this.winningsCount = winningsCount;
    }

    public Integer getDefeatsCount() {
        return defeatsCount;
    }

    public void setDefeatsCount(Integer defeatsCount) {
        this.defeatsCount = defeatsCount;
    }

    public Integer getBankruptsCount() {
        return bankruptsCount;
    }

    public void setBankruptsCount(Integer bankruptsCount) {
        this.bankruptsCount = bankruptsCount;
    }

    public GamerAccount getSecondGamerAccount() {
        return secondGamerAccount;
    }

    public void setSecondGamerAccount(GamerAccount secondGamerAccount) {
        this.secondGamerAccount = secondGamerAccount;
    }

    public GameVariant getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }
}
