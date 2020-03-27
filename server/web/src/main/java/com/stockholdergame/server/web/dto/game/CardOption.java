package com.stockholdergame.server.web.dto.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CardOption {

    public int major;

    public int minor;

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(major)
                .append(minor).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CardOption)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final CardOption co = (CardOption) obj;

        return new EqualsBuilder()
                .append(major, co.major)
                .append(minor, co.minor)
                .isEquals();
    }
}
