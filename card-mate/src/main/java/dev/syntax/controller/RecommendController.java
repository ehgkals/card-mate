package dev.syntax.controller;

import dev.syntax.model.dto.Card;
import dev.syntax.service.RecommendationService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class RecommendController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RecommendationService service = new RecommendationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");
        resp.getWriter().write("recommend endpoint OK");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        if (username != null) username = username.trim();

        List<Card> cards = (username == null || username.isEmpty())
                ? java.util.Collections.emptyList()
                : service.recommendByUserName(username);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        StringBuilder out = new StringBuilder();
        if (cards == null || cards.isEmpty()) {
            out.append("<p>추천 결과가 없습니다. (사용자 없음 / 소비 내역 없음 / 실적 요건 미달)</p>");
        } else {
            out.append("<table>")
               .append("<thead><tr>")
               .append("<th>#</th>")
               .append("<th>이미지</th>")
               .append("<th>카드명</th><th>유형</th>")
               .append("<th class='right'>연회비</th><th class='right'>실적</th>")
               .append("<th class='right'>총소비</th><th class='right'>순이익</th>")
               .append("</tr></thead><tbody>");

            int i = 1;
            for (Card c : cards) {
                out.append("<tr>")
                   .append("<td>").append(i++).append("</td>");

                // 이미지
                out.append("<td class='img-col'>");
                String b64 = c.getCardImage();
                if (b64 != null && !b64.isEmpty()) {
                    out.append("<div class='img-wrap'>")
                       .append("<img class='card-img' src='data:image/jpeg;base64,")
                       .append(b64)
                       .append("' alt='").append(escape(c.getCardName())).append("'>")
                       .append("</div>");
                } else {
                    out.append("<div class='img-wrap' style='color:#aaa;display:flex;align-items:center;justify-content:center;'>이미지 없음</div>");
                }
                out.append("</td>");

                // 텍스트/금액(콤마 포맷) + 타입 한글 매핑
                out.append("<td>").append(escape(c.getCardName())).append("</td>")
                   .append("<td>").append(korType(c.getCardType())).append("</td>")
                   .append("<td class='right'>").append(won(c.getAnnualFee())).append("</td>")
                   .append("<td class='right'>").append(won(c.getPerformanceReq())).append("</td>")
                   .append("<td class='right'>").append(won(c.getTotalSpend())).append("</td>")
                   .append("<td class='right'><b>").append(won(c.getNetBenefit())).append("</b></td>")
                   .append("</tr>");
            }
            out.append("</tbody></table>");
        }
        resp.getWriter().write(out.toString());
    }

    // 금액 포맷: 1,234,567원
    private String won(BigDecimal v) {
        if (v == null) return "0원";
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(v.setScale(0, RoundingMode.HALF_UP)) + "원";
    }

    // 카드 타입 영문 → 한글 매핑
    private String korType(String type) {
        if (type == null) return "";
        String t = type.toLowerCase(Locale.ROOT);
        if ("credit".equals(t)) return "신용카드";
        if ("check".equals(t) || "debit".equals(t)) return "체크카드";
        return escape(type); // 혹시 모르는 값은 원문 표시
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;")
                .replace("'","&#x27;");
    }
}


