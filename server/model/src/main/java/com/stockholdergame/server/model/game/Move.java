package com.stockholdergame.server.model.game;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_moves",
        uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "move_number"}))
public class Move implements Serializable, Comparable<Move> {

    private static final long serialVersionUID = 8376896577357107602L;

    @Id
    @GeneratedValue
    @Column(name = "move_id")
    private Long id;

    @Column(name = "move_number", nullable = false)
    private Integer moveNumber;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToMany(mappedBy = "move", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<CompetitorMove> competitorMoves;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<CompetitorMove> getCompetitorMoves() {
        return competitorMoves;
    }

    public void setCompetitorMoves(Set<CompetitorMove> competitorMoves) {
        this.competitorMoves = competitorMoves;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("moveNumber", moveNumber)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move)) {
            return false;
        }
        Move g = (Move) o;
        return new EqualsBuilder()
                .append(moveNumber, g.moveNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(moveNumber)
                .toHashCode();
    }

    public int compareTo(Move o) {
        return this.moveNumber - o.moveNumber;
    }
}
