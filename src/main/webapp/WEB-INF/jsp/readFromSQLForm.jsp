<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Factory Info</title>
</head>
<body>
 <form action="readDataFromSQL" method="get" accept-charset="UTF-8">
 SQL : <input type="text" name="SQLQuery" maxlength="100" style="width:300px;height:100px">
 <input type="hidden" name="QueryType" value="SQL">
 <input type="submit" value="전송" >
 <input type="reset" value="취소">
</form><br><br>
 <form action="readDataFromSQL" method="get" accept-charset="UTF-8">
 JPQL: <input type="text" name="SQLQuery" maxlength="100" style="width:300px;height:100px">
 <input type="hidden" name="QueryType" value="JPQL">
 <input type="submit" value="전송" >
 <input type="reset" value="취소">
</form>
</body>
</html>