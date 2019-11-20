package com.stockholdergame.server.model.game;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_competitor_cards")
public class CompetitorCard implements Serializable {

    private static final long serialVersionUID = -7926559088414279405L;

    @Id
    @GeneratedValue
    @Column(name = "competitor_card_id")
    private Long id;

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @Column(name = "is_applied")
    @Type(type = "boolean")
    private Boolean isApplied = false;

    @ManyToOne
    @JoinColumn(name = "competitor_id")
    private Competitor competitor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    public void setApplied(Boolean applied) {
        isApplied = applied;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }
}
