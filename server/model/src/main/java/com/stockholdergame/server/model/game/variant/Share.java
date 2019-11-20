package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_shares")
public class Share implements Identifiable<Long> {

    private static final long serialVersionUID = -2019497063951785228L;

    @Id
    @GeneratedValue
    @Column(name = "share_id")
    private Long id;

    @OneToMany(mappedBy = "share", fetch = EAGER)
    private Set<GameShare> gameShares;

    @OneToMany(mappedBy = "share", fetch = EAGER)
    private Set<CardOperation> cardOperations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<GameShare> getGameShares() {
        return gameShares;
    }

    public void setGameShares(Set<GameShare> gameShares) {
        this.gameShares = gameShares;
    }

    public Set<CardOperation> getCardOperations() {
        return cardOperations;
    }

    public void setCardOperations(Set<CardOperation> cardOperations) {
        this.cardOperations = cardOperations;
    }
}
