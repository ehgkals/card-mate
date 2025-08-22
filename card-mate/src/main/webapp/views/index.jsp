<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>맞춤 카드 추천</title>
<style>
:root { --gap:14px; }

/* 페이지 레이아웃 */
body {
  display:flex; justify-content:center; align-items:flex-start;
  min-height:100vh; margin:0; flex-direction:column;
}
.container{
  text-align:center;
  width:min(1280px, 95vw);   /* ★ 좌우 널널 (1280px) */
  margin:0 auto;             /* ★ 중앙 정렬 */
  padding:20px 0;
  overflow-x:visible;        /* 안 잘리게 */
}
img.logo { max-width:200px; height:auto; }

/* 테이블 */
table{ border-collapse:collapse; width:100%; margin:var(--gap) auto 0; table-layout:fixed; }
th,td{ border:1px solid #ddd; padding:10px; text-align:center; vertical-align:middle; }
th{ background:#f6f6f6; }
.right{ text-align:center !important; }

/* ▼ 컬럼 폭 (총합=1180px → 컨테이너 1280px 안에 안전하게 들어감) */
table thead th:nth-child(1),
table tbody td:nth-child(1){ width:60px; }         /* # */
table thead th:nth-child(2),
table tbody td:nth-child(2){ width:180px; }        /* 이미지 */
table thead th:nth-child(3),
table tbody td:nth-child(3){
  width:320px;                                      /* 카드명 */
  text-align:left; padding-left:12px;
  white-space:normal; word-break:break-word; line-height:1.35;
}
table thead th:nth-child(4),
table tbody td:nth-child(4){ width:100px; }        /* 유형 */
table thead th:nth-child(5),
table tbody td:nth-child(5){ width:120px; }        /* 연회비 */
table thead th:nth-child(6),
table tbody td:nth-child(6){ width:120px; }        /* 실적 */
table thead th:nth-child(7),
table tbody td:nth-child(7){ width:140px; }        /* 총소비 */
table thead th:nth-child(8),
table tbody td:nth-child(8){ width:140px; }        /* 순이익 */

/* 이미지 박스 */
.img-col{ padding:6px; }
.img-col img{
  width:120px; height:160px;
  object-fit:contain; display:block; margin:0 auto;
  border-radius:10px; background:#fafafa;
}

/* 기타 */
#result{ margin-top:20px; font-size:16px; }
#loading{ display:none; margin-top:12px; color:#666; font-size:14px; }

/* 작은 화면에서만 스크롤 허용(옵션) */
@media (max-width: 1100px){
  .container{ width:95vw; overflow-x:auto; }
}

</style>
<script>
  function sendForm(event) {
    event.preventDefault();

    const input = document.querySelector("input[name='username']");
    const username = input.value.trim();
    if (!username) return;

    const ctx = '<%=request.getContextPath()%>'; // ex) /card-mate
    const resultEl = document.getElementById("result");
    const loadingEl = document.getElementById("loading");

    resultEl.innerHTML = "";
    loadingEl.style.display = "block";

    fetch(ctx + "/recommend", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
      body: "username=" + encodeURIComponent(username)
    })
    .then(res => res.text())
    .then(html => {
      loadingEl.style.display = "none";
      resultEl.innerHTML = html; // 서버가 내려준 <table> 렌더
    })
    .catch(err => {
      console.error(err);
      loadingEl.style.display = "none";
      resultEl.innerHTML = "<p style='color:red'>오류가 발생했습니다.</p>";
    });
  }
</script>
</head>
<body>
  <div class="container">
    <img class="logo" src="https://pc.wooricard.com/dcpc/img/pc/common/h1_logo.png" alt="로고" />
    <h1>나의 맞춤카드 찾기</h1>
    <p>이름을 입력하시면 카드 추천을 보여드립니다.</p>

    <form onsubmit="sendForm(event)">
      <input type="text" name="username" placeholder="이름 입력" required />
      <br><br>
      <input type="submit" value="추천받기" />
    </form>

    <div id="loading">추천 결과를 불러오는 중...</div>
    <div id="result"></div>
  </div>
</body>
</html>
