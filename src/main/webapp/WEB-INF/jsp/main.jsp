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
<a href="/sessionTest">testDBCoreSesion</a> : session test <br>
<a href="/reaAlldData">/reaAlldDataTest</a> : data Read Test <br>
<a href="/readDataWithKey">readData with key</a> : data Read Test <br>
</body>
</html>