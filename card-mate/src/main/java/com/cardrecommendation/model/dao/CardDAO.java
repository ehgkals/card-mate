package com.cardrecommendation.model.dao;
// DAO DB의 data에 접근하는 객체
import com.cardrecommendation.model.dto.Card;
import com.cardrecommendation.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    /**
     * 유저의 월 소비(목데이터)를 바탕으로 카드별 순이익(net_benefit) 순으로 정렬.
     * benefit_rate는 %단위 → (rate/100.0)으로 계산.
     * 실적조건은 유저 총 소비액이 performance_req 이상인 카드만 노출.
     */
    public List<Card> getRecommendedCards(long userId) {
        String sql = //추천로직
            "SELECT c.card_id, c.card_name, c.card_type, c.annual_fee, c.performance_req, " +
            "       ROUND(SUM(COALESCE(t.total_amount,0) * (cb.benefit_rate/100.0)) - c.annual_fee, 2) AS net_benefit, " +
            "       ts.total_spend " +
            "FROM card c " +
            "JOIN card_benefit cb ON cb.card_id = c.card_id " +
            "LEFT JOIN ( " +
            "    SELECT category_id, SUM(amount) AS total_amount " +
            "    FROM transaction WHERE user_id = ? GROUP BY category_id " +
            ") t ON t.category_id = cb.category_id " +
            "CROSS JOIN ( " +
            "    SELECT COALESCE(SUM(amount),0) AS total_spend FROM transaction WHERE user_id = ? " +
            ") ts " +
            "GROUP BY c.card_id, c.card_name, c.card_type, c.annual_fee, c.performance_req, ts.total_spend " +
            "HAVING ts.total_spend >= c.performance_req " +
            "ORDER BY net_benefit DESC";

        List<Card> result = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setLong(2, userId);

            try (ResultSet rs = ps.executeQuery()) { //여기서 DB에 저장된 목데이터 SELECT 실행
                while (rs.next()) { // rs.next() 돌면서 DB에서 조회된 데이터 꺼내오기
                    Card c = new Card();
                    c.setCardId(rs.getLong("card_id"));
                    c.setCardName(rs.getString("card_name"));
                    c.setCardType(rs.getString("card_type"));
                    c.setAnnualFee(rs.getBigDecimal("annual_fee"));
                    c.setPerformanceReq(rs.getBigDecimal("performance_req"));
                    c.setNetBenefit(rs.getBigDecimal("net_benefit"));
                    c.setTotalSpend(rs.getBigDecimal("total_spend"));
                    result.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getRecommendedCards failed", e);
        }
        return result; // Controller → JSP 로 전달
    }
}

