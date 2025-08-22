package dev.syntax.model.dto;

import java.math.BigDecimal;

public class CardDTO {
	private long cardId;
	private String cardName;
	private String cardType;
	private BigDecimal annualFee;
	private BigDecimal performanceReq;
	private long[] categoryID;
	private BigDecimal[] benefitRate;

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(BigDecimal annualFee) {
		this.annualFee = annualFee;
	}

	public BigDecimal getPerformanceReq() {
		return performanceReq;
	}

	public void setPerformanceReq(BigDecimal performanceReq) {
		this.performanceReq = performanceReq;
	}

	public long[] getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long[] categoryID) {
		this.categoryID = categoryID;
	}

	public BigDecimal[] getBenefitRate() {
		return benefitRate;
	}

	public void setBenefitRate(BigDecimal[] benefitRate) {
		this.benefitRate = benefitRate;
	}

}
