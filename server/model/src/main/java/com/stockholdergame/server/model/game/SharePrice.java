package com.stockholdergame.server.model.game;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_share_prices")
public class SharePrice implements Serializable, Comparable<SharePrice> {

    private static final long serialVersionUID = 4693871662204980623L;

    @Id
    private SharePricePk id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "price_oper_id")
    private Long priceOperationId;

    @MapsId("stepId")
    @ManyToOne
    @JoinColumn(name = "step_id")
    private MoveStep step;

    public SharePricePk getId() {
        return id;
    }

    public void setId(SharePricePk id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getPriceOperationId() {
        return priceOperationId;
    }

    public void setPriceOperationId(Long priceOperationId) {
        this.priceOperationId = priceOperationId;
    }

    public MoveStep getStep() {
        return step;
    }

    public void setStep(MoveStep step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("price", price)
                .append("priceOperationId", priceOperationId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SharePrice)) {
            return false;
        }
        SharePrice g = (SharePrice) o;
        return new EqualsBuilder()
                .append(id, g.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    public int compareTo(SharePrice o) {
        return (int) (this.id.getShareId() - o.id.getShareId());
    }
}
