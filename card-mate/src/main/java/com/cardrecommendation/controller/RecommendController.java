package com.cardrecommendation.controller;

import com.cardrecommendation.model.dto.Card;
import com.cardrecommendation.model.service.RecommendationService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RecommendController extends HttpServlet {

    private final RecommendationService service = new RecommendationService();

    // GET은 간단 헬스체크 (원하면 AJAX도 GET으로 호출 가능)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");
        resp.getWriter().write("recommend endpoint OK");
    }

    // AJAX POST → HTML 테이블 조각 반환
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username"); // 목데이터 단계에선 사용 안 함

        List<Card> cards = service.recommendStatic(); // userId=1 기준 목데이터

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        StringBuilder out = new StringBuilder();
        if (cards == null || cards.isEmpty()) {
            out.append("<p>추천 결과가 없습니다. (소비 내역이 없거나 실적 요건 미달)</p>");
        } else {
            out.append("<table>")
               .append("<thead><tr>")
               .append("<th>#</th><th>카드명</th><th>유형</th>")
               .append("<th class='right'>연회비</th><th class='right'>실적</th>")
               .append("<th class='right'>총소비</th><th class='right'>순이익</th>")
               .append("</tr></thead><tbody>");

            int i = 1;
            for (Card c : cards) {
                out.append("<tr>")
                   .append("<td>").append(i++).append("</td>")
                   .append("<td>").append(escape(c.getCardName())).append("</td>")
                   .append("<td>").append(escape(c.getCardType())).append("</td>")
                   .append("<td class='right'>").append(c.getAnnualFee().intValue()).append("원</td>")
                   .append("<td class='right'>").append(c.getPerformanceReq().intValue()).append("원</td>")
                   .append("<td class='right'>").append(c.getTotalSpend().intValue()).append("원</td>")
                   .append("<td class='right'><b>").append(c.getNetBenefit().intValue()).append("원</b></td>")
                   .append("</tr>");
            }
            out.append("</tbody></table>");
        }
        resp.getWriter().write(out.toString());
    }

    // 간단 escape (XSS 최소화)
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;")
                .replace("'","&#x27;");
    }
}
