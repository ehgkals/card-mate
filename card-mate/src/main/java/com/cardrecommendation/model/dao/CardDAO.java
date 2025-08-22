package com.cardrecommendation.model.dao;
// DAO DB의 data에 접근하는 객체
import com.cardrecommendation.model.dto.Card;
import com.cardrecommendation.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CardDAO {

    /**
     * 유저의 월 소비(목데이터)를 바탕으로 카드별 순이익(net_benefit) 순으로 정렬.
     * benefit_rate는 %단위 → (rate/100.0)으로 계산.
     * 실적조건은 유저 총 소비액이 performance_req 이상인 카드만 노출.
     */
    public List<Card> getRecommendedCards(long userId) {
        String sql =
            "WITH\n" +
            "params AS ( SELECT ? AS uid ),\n" +
            "lm AS (\n" +
            "  SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01') AS start_dt,\n" +
            "         DATE_FORMAT(CURDATE(), '%Y-%m-01') AS end_dt\n" +
            "),\n" +
            "user_cat_spend AS (\n" +
            "  SELECT t.category_id, SUM(t.amount) AS cat_spend\n" +
            "  FROM `transaction` t\n" +
            "  JOIN params p ON t.user_id = p.uid\n" +
            "  JOIN lm ON t.transaction_date >= lm.start_dt AND t.transaction_date < lm.end_dt\n" +
            "  GROUP BY t.category_id\n" +
            "),\n" +
            "user_total_spend AS (\n" +
            "  SELECT SUM(t.amount) AS total_spend\n" +
            "  FROM `transaction` t\n" +
            "  JOIN params p ON t.user_id = p.uid\n" +
            "  JOIN lm ON t.transaction_date >= lm.start_dt AND t.transaction_date < lm.end_dt\n" +
            ")\n" +
            "SELECT \n" +
            "  c.card_id,\n" +
            "  c.card_name,\n" +
            "  c.card_type,\n" +
            "  c.annual_fee,\n" +
            "  c.performance_req,\n" +
            "  c.card_image,\n" +
            "  ROUND(SUM(ucs.cat_spend * (cb.benefit_rate/100.0)), 0)                         AS expected_benefit,\n" +
            "  ROUND(SUM(ucs.cat_spend * (cb.benefit_rate/100.0)) - (c.annual_fee/12.0), 0)   AS net_benefit,\n" +
            "  uts.total_spend\n" +
            "FROM card c\n" +
            "JOIN card_benefit cb    ON cb.card_id = c.card_id\n" +
            "JOIN user_cat_spend ucs ON ucs.category_id = cb.category_id\n" +
            "CROSS JOIN user_total_spend uts\n" +
            "WHERE uts.total_spend >= c.performance_req\n" +
            "GROUP BY c.card_id, c.card_name, c.card_type, c.annual_fee, c.performance_req, c.card_image, uts.total_spend\n" +
            "ORDER BY net_benefit DESC, expected_benefit DESC, c.card_id ASC\n" +
            "LIMIT 3";

        List<Card> result = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Card c = new Card();
                    c.setCardId(rs.getLong("card_id"));
                    c.setCardName(rs.getString("card_name"));
                    c.setCardType(rs.getString("card_type"));
                    c.setAnnualFee(rs.getBigDecimal("annual_fee"));
                    c.setPerformanceReq(rs.getBigDecimal("performance_req"));
                    c.setNetBenefit(rs.getBigDecimal("net_benefit"));
                    c.setTotalSpend(rs.getBigDecimal("total_spend"));

                    // BLOB → Base64 문자열
                    byte[] img = rs.getBytes("card_image");
                    if (img != null && img.length > 0) {
                        c.setCardImage(Base64.getEncoder().encodeToString(img));
                    }

                    result.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getRecommendedCards failed", e);
        }
        return result;
    }
}

