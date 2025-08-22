package com.cardrecommendation.model.dto;

import java.math.BigDecimal;

public class CardScore {
    private long cardId;
    private String cardName;
    private BigDecimal netBenefit;

    public CardScore(long cardId, String cardName, BigDecimal netBenefit) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.netBenefit = netBenefit;
    }

    public long getCardId() { return cardId; }
    public String getCardName() { return cardName; }
    public BigDecimal getNetBenefit() { return netBenefit; }
}
