<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>맞춤 카드 추천</title>
<style>
  body { display:flex; justify-content:center; align-items:center; height:100vh; margin:0; flex-direction:column; }
  .container { text-align:center; width:min(900px,90vw); }
  img { max-width: 240px; height:auto; }
  table { border-collapse: collapse; width:100%; margin-top:14px; }
  th, td { border:1px solid #ddd; padding:10px; text-align:left; }
  th { background:#f6f6f6; }
  .right { text-align:right; }
  #result { margin-top: 20px; font-size: 16px; }
</style>
<script>
  function sendForm(event) {
    event.preventDefault();

    const username = document.querySelector("input[name='username']").value.trim();
    if (!username) return;

    const ctx = '<%=request.getContextPath()%>'; // ex) /card-mate

    fetch(ctx + "/recommend", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
      body: "username=" + encodeURIComponent(username)
    })
    .then(res => res.text())
    .then(html => { document.getElementById("result").innerHTML = html; })
    .catch(err => {
      console.error(err);
      document.getElementById("result").innerHTML = "<p style='color:red'>오류가 발생했습니다.</p>";
    });
  }
</script>
</head>
<body>
  <div class="container">
    <img src="https://pc.wooricard.com/dcpc/img/pc/common/h1_logo.png" alt="로고" />
    <h1>나의 맞춤카드 찾기</h1>
    <p>이름을 입력하시면(목데이터 기준) 추천을 보여드립니다.</p>

    <form onsubmit="sendForm(event)">
      <input type="text" name="username" placeholder="이름 입력" required />
      <br><br>
      <input type="submit" value="추천받기" />
    </form>

    <div id="result"></div>
  </div>
</body>
</html>
