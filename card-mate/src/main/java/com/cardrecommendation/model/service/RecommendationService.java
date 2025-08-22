package com.cardrecommendation.model.service;

import com.cardrecommendation.model.dao.CardDAO;
import com.cardrecommendation.model.dao.UserDAO;
import com.cardrecommendation.model.dto.Card;

import java.util.Collections;
import java.util.List;

public class RecommendationService {
    private final CardDAO cardDAO = new CardDAO();
    private final UserDAO userDAO = new UserDAO();

    // (기존) 목데이터용은 남겨도 되지만 실제 화면에선 쓰지 않기
    public List<Card> recommendStatic() {
        long mockUserId = 1L;
        return cardDAO.getRecommendedCards(mockUserId);
    }

    public List<Card> recommendForUser(long userId) {
        return cardDAO.getRecommendedCards(userId);
    }

    // ✅ 이름으로 들어오면 여기서 user_id를 찾아 실제 추천 실행
    public List<Card> recommendByUserName(String userName) {
        Long userId = userDAO.findUserIdByName(userName);
        if (userId == null) return Collections.emptyList(); // 사용자 없음
        return cardDAO.getRecommendedCards(userId);
    }
}
