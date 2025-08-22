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
    }
    .container {
        text-align: center;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>나의 맞춤카드 찾기</h1>
        <p>이름을 입력하시면 카드를 추천해드립니다.</p>
        <form action="recommendCard" method="post">
            <input type="text" name="username" placeholder="이름 입력" required />
            <br><br>
            <input type="submit" value="추천받기" />
        </form>
    </div>
</body>
</html>