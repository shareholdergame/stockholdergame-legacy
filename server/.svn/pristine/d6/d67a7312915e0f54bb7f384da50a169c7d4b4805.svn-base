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
@Table(name = "ag_share_quantities")
public class ShareQuantity implements Serializable {

    private static final long serialVersionUID = -3255411359039640170L;

    @Id
    private ShareQuantityPk id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "buy_sell_quantity", nullable = false)
    private Integer buySellQuantity = 0;

    @MapsId("stepId")
    @ManyToOne
    @JoinColumn(name = "step_id")
    private MoveStep step;

    public ShareQuantityPk getId() {
        return id;
    }

    public void setId(ShareQuantityPk id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBuySellQuantity() {
        return buySellQuantity;
    }

    public void setBuySellQuantity(Integer buySellQuantity) {
        this.buySellQuantity = buySellQuantity;
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
                .append("quantity", quantity)
                .append("buySellQuantity", buySellQuantity)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ShareQuantity)) {
            return false;
        }
        ShareQuantity g = (ShareQuantity) o;
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
}
