package dev.syntax.service;

import com.cardrecommendation.model.dao.CardDAO;
import com.cardrecommendation.model.dto.Card;

import java.util.List;

public class RecommendationService {
    private final CardDAO cardDAO = new CardDAO();

    // 현재 단계: 입력 없이 userId 고정(=1) 목데이터 기준 추천
    public List<Card> recommendStatic() {
        long mockUserId = 1L;
        return cardDAO.getRecommendedCards(mockUserId);
    }

    // 다음 단계(시간 나면): 특정 userId 또는 폼 입력 받아 동적 추천
    public List<Card> recommendForUser(long userId) {
        return cardDAO.getRecommendedCards(userId);
    }
}
