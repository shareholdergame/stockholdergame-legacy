package com.stockholdergame.server.dto;

/**
 *
 */
public class PaginationDto {

    public static final int DEFAULT_MAX_RESULTS = 100;

    private int offset;

    private int maxResults = DEFAULT_MAX_RESULTS;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset >= 0 ? offset : 0;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults > 0 ? maxResults : DEFAULT_MAX_RESULTS;
    }
}
