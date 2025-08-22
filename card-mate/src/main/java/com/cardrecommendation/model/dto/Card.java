package com.cardrecommendation.model.dto;
// DTO는 계층간 데이터 교환을 위한 객체  데이터를 교환하는 목적을 갖는 객체이기 때문에, 서비스 로직을 갖고 있지 않다.
import java.math.BigDecimal;

public class Card {
    private long cardId;
    private String cardName;
    private String cardType;
    private BigDecimal annualFee;
    private BigDecimal performanceReq;
    private String cardImage; 

    // 계산 결과 표시용
    private BigDecimal netBenefit; // 총혜택 - 연회비
    private BigDecimal totalSpend; // 유저 총 소비

    public long getCardId() { return cardId; }
    public void setCardId(long cardId) { this.cardId = cardId; }
    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public BigDecimal getAnnualFee() { return annualFee; }
    public void setAnnualFee(BigDecimal annualFee) { this.annualFee = annualFee; }
    public BigDecimal getPerformanceReq() { return performanceReq; }
    public void setPerformanceReq(BigDecimal performanceReq) { this.performanceReq = performanceReq; }
    public BigDecimal getNetBenefit() { return netBenefit; }
    public void setNetBenefit(BigDecimal netBenefit) { this.netBenefit = netBenefit; }
    public BigDecimal getTotalSpend() { return totalSpend; }
    public void setTotalSpend(BigDecimal totalSpend) { this.totalSpend = totalSpend; }
    public String getCardImage() {
        return cardImage;
    }
    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }
}
