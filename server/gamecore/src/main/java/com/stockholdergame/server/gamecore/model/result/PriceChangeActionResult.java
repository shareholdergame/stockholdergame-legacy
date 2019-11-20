package com.stockholdergame.server.gamecore.model.result;

import com.stockholdergame.server.gamecore.Compensation;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alexander Savin
 */
public class PriceChangeActionResult implements Comparable<PriceChangeActionResult> {

    private Long shareId;

    private int newPrice;

    private TreeMap<Integer, Compensation> competitorCompensationsMap;

    public PriceChangeActionResult(Long shareId, int newPrice,
                                   Map<Integer, Compensation> competitorCompensationsMap) {
        Validate.notNull(shareId);

        this.shareId = shareId;
        this.newPrice = newPrice;
        this.competitorCompensationsMap = competitorCompensationsMap != null ?
            new TreeMap<>(competitorCompensationsMap) : null;
    }

    public Long getShareId() {
        return shareId;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public Map<Integer, Compensation> getCompetitorCompensationsMap() {
        return Collections.unmodifiableSortedMap(competitorCompensationsMap);
    }

    @Override
    public int compareTo(PriceChangeActionResult o) {
        if (this == o) {
            return 0;
        }
        return (int) (shareId - o.shareId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceChangeActionResult that = (PriceChangeActionResult) o;

        if (newPrice != that.newPrice) return false;
        if (!shareId.equals(that.shareId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shareId.hashCode();
        result = 31 * result + newPrice;
        return result;
    }
}
