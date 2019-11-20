package com.stockholdergame.server.dto.game;

import java.util.Date;
import java.util.Set;

/**
 * @author Alexander Savin
 */
public class CompetitorMoveDto implements Comparable<CompetitorMoveDto> {

    private Integer moveNumber;

    private Integer moveOrder;

    private Long appliedCardId;

    private Date finishedTime;

    private Set<MoveStepDto> steps;

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public Long getAppliedCardId() {
        return appliedCardId;
    }

    public void setAppliedCardId(Long appliedCardId) {
        this.appliedCardId = appliedCardId;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Set<MoveStepDto> getSteps() {
        return steps;
    }

    public void setSteps(Set<MoveStepDto> steps) {
        this.steps = steps;
    }

    @Override
    public int compareTo(CompetitorMoveDto o) {
        int result = this.moveOrder - o.moveOrder;
        if (result == 0) {
            result = this.moveNumber - o.moveNumber;
        }
        return result;
    }
}
