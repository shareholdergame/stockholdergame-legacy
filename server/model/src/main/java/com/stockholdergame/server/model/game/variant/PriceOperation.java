package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name ="gv_price_operations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"price_oper_type_id", "operand_value"}))
public class PriceOperation implements Identifiable<Long> {

    private static final long serialVersionUID = -945164844282299061L;

    @Id
    @GeneratedValue
    @Column(name = "price_oper_id")
    private Long id;

    @Enumerated
    @Column(name = "price_oper_type_id", nullable = false)
    private PriceOperationType priceOperationType;

    @Column(name = "operand_value", nullable = false)
    private Integer operandValue;

    @OneToMany(mappedBy = "priceOperation", fetch = EAGER)
    private Set<CardOperation> cardOperations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceOperationType getPriceOperationType() {
        return priceOperationType;
    }

    public void setPriceOperationType(PriceOperationType priceOperationType) {
        this.priceOperationType = priceOperationType;
    }

    public Integer getOperandValue() {
        return operandValue;
    }

    public void setOperandValue(Integer operandValue) {
        this.operandValue = operandValue;
    }

    public Set<CardOperation> getCardOperations() {
        return cardOperations;
    }

    public void setCardOperations(Set<CardOperation> cardOperations) {
        this.cardOperations = cardOperations;
    }
}
