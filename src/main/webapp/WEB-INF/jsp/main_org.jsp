<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajx/libs/jquery/3.1.1/jquery.min.js"></script>
<% String path=request.getContextPath();
%>
<body>
<a href="SessionPoolTestForm">Session test</a> : session pool test <br>
<a href="reaAlldData?sourceType=DAOImpl">reaAlldDataTest</a> : using FactoryDAO->update, delete <br>
<a href="insertFactoryForm">insert Factory</a> : using FactoryDAO <br>
<a href="readFromSQLForm">reaAlldData using SQL/JPQL Test</a> : data Read Test <br>
<a href="reaAlldData?sourceType=Annotation">update Factory using auto commit</a>: write Factory without Begin/End Session<br>
</body>
</html>