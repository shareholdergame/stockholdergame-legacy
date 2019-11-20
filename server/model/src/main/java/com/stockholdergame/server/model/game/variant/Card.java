package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.BatchSize;

import static com.stockholdergame.server.model.ModelConstants.TINY_BATCH_SIZE;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_cards")
public class Card implements Identifiable<Long> {

    private static final long serialVersionUID = -2296611746280366592L;

    @Id
    @GeneratedValue
    @Column(name = "card_id")
    private Long id;

    @OneToMany(mappedBy = "card", fetch = EAGER)
    @BatchSize(size = TINY_BATCH_SIZE)
    private Set<CardOperation> cardOperations;

    @OneToMany(mappedBy = "card", fetch = EAGER)
    private Set<CardQuantity> cardQuantities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CardOperation> getCardOperations() {
        return cardOperations;
    }

    public void setCardOperations(Set<CardOperation> cardOperations) {
        this.cardOperations = cardOperations;
    }

    public Set<CardQuantity> getCardQuantities() {
        return cardQuantities;
    }

    public void setCardQuantities(Set<CardQuantity> cardQuantities) {
        this.cardQuantities = cardQuantities;
    }
}
