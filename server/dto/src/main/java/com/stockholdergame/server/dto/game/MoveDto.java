package com.stockholdergame.server.dto.game;

import java.util.Set;

/**
 * @author Alexander Savin
 */
public class MoveDto implements Comparable<MoveDto> {

    private Integer moveNumber;

    private Set<CompetitorMoveDto> competitorMoves;

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Set<CompetitorMoveDto> getCompetitorMoves() {
        return competitorMoves;
    }

    public void setCompetitorMoves(Set<CompetitorMoveDto> competitorMoves) {
        this.competitorMoves = competitorMoves;
    }

    @Override
    public int compareTo(MoveDto o) {
        return this.moveNumber - o.moveNumber;
    }
}
