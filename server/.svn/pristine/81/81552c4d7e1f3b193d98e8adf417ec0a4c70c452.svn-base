package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.BatchSize;

import static com.stockholdergame.server.model.ModelConstants.SMALL_BATCH_SIZE;
import static com.stockholdergame.server.model.ModelConstants.TINY_BATCH_SIZE;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_card_groups")
public class CardGroup implements Identifiable<Long> {

    private static final long serialVersionUID = -7259520650547808815L;

    @Id
    @Column(name = "card_group_id", nullable = false)
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "cardGroup", fetch = EAGER)
    @BatchSize(size = SMALL_BATCH_SIZE)
    private Set<CardQuantity> cardQuantities;

    @OneToMany(mappedBy = "cardGroup", fetch = EAGER)
    @BatchSize(size = TINY_BATCH_SIZE)
    private Set<GameCardGroup> gameCardGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<CardQuantity> getCardQuantities() {
        return cardQuantities;
    }

    public void setCardQuantities(Set<CardQuantity> cardQuantities) {
        this.cardQuantities = cardQuantities;
    }

    public Set<GameCardGroup> getGameCardGroups() {
        return gameCardGroups;
    }

    public void setGameCardGroups(Set<GameCardGroup> gameCardGroups) {
        this.gameCardGroups = gameCardGroups;
    }
}
