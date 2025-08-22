<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맞춤 카드 추천</title>
<style>
body {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	flex-direction: column; /* 세로 배치 */
}

.container {
	text-align: center;
}

img {
	max-width: 400px;
	height: auto;
}

#result {
	margin-top: 20px;
	font-size: 18px;
	color: blue;
}
</style>
<script>
	function sendForm(event) {
		event.preventDefault(); // 폼 전송으로 인한 페이지 이동 막기

		const username = document.querySelector("input[name='username']").value;

		fetch("recommendCard", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: "username=" + encodeURIComponent(username)
		})
		.then(response => response.text())
		.then(data => {
			document.getElementById("result").innerHTML = data;
		})
		.catch(error => console.error("Error:", error));
	}
</script>
</head>
<body>
	<div class="container">
		<img src="https://pc.wooricard.com/dcpc/img/pc/common/h1_logo.png"
			alt="로고" />
		<h1>나의 맞춤카드 찾기</h1>
		<p>이름을 입력하시면 카드를 추천해드립니다.</p>

		<form onsubmit="sendForm(event)">
			<input type="text" name="username" placeholder="이름 입력" required /> <br>
			<br> <input type="submit" value="추천받기" />
		</form>

		<!-- 결과 표시 영역 -->
		<div id="result"></div>
	</div>
</body>
</html>