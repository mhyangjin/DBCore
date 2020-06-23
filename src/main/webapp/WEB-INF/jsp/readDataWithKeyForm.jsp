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
${sourceType}<br>
 <form action="deleteDataWithKey" method="get" accept-charset="UTF-8">
<input type="hidden" name="memberCorpid" value="${FactoryId.memberCorpid}">
<input type="hidden" name="factoryid" value="${FactoryId.factoryid}">
<input type="hidden" name="sourceType" value="${sourceType}">
<input type="submit" value="삭제" >
</form>
<form name="FactoryInfo" action="updateDataWithKey" method="get" accept-charset="UTF-8">
<table > 
<tr><td>memberCorpid:</td><td>${FactoryId.memberCorpid}</td></tr>
<tr><td>factoryid   :</td><td>${FactoryId.factoryid}</td></tr>
<tr><td>bizNo       :</td><td><input type="text" name=bizNo value="${FactoryId.bizNo}"></td></tr>
<tr><td>factorytype :</td><td><input type="text" name=factorytype value="${FactoryId.factorytype}"></td></tr>
<tr><td>fctryNm     :</td><td><input type="text" name=fctryNm value="${FactoryId.fctryNm}"></td></tr>
<tr><td>erpcode     :</td><td><input type="text" name=erpcode value="${FactoryId.erpcode}"></td></tr>
<tr><td>tph1        :</td><td><input type="text" name=tph1 value="${FactoryId.tph1}"></td></tr>
</table>
<input type="hidden" name="memberCorpid" value="${FactoryId.memberCorpid}">
<input type="hidden" name="factoryid" value="${FactoryId.factoryid}">
<input type="hidden" name="sourceType" value="${sourceType}">
<input type="submit" value="수정" ><br>
</form>
<br>
</body>
</html>