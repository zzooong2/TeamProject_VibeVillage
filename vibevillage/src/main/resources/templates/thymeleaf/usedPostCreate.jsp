<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello thymeleaf</title>
</head>
<body>
<h1>Hello thymeleaf</h1>

<!-- Thymeleaf 사용 -->
<div th:if="true">
    <div th:text="${result}">${result} 연결 완료? </div>
</div>

</body>
</html>