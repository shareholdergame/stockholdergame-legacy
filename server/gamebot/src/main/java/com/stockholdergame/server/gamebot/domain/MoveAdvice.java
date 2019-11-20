package com.stockholdergame.server.gamebot.domain;

import java.util.Collections;
import java.util.Set;

/**
 *
 */
public class MoveAdvice {

    private Set<Long> sharesToIncrease;

    private Set<Long> sharesToDecrease;

    public MoveAdvice(Set<Long> sharesToIncrease, Set<Long> sharesToDecrease) {
        this.sharesToIncrease = sharesToIncrease;
        this.sharesToDecrease = sharesToDecrease;
    }

    public Set<Long> getSharesToIncrease() {
        return Collections.unmodifiableSet(sharesToIncrease);
    }

    public Set<Long> getSharesToDecrease() {
        return Collections.unmodifiableSet(sharesToDecrease);
    }
}
