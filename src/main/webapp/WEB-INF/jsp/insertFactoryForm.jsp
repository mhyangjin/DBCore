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
 <form name="Factory" action="insertFactory" method="get" accept-charset="UTF-8">
<table > 
<tr><td>memberCorpid:</td><td><input type="text" name=memberCorpid value="${FactoryId.memberCorpid}"></td></tr>
<tr><td>factoryid   :</td><td><input type="text" name=factoryid value="${FactoryId.factoryid}"></td></tr>
<tr><td>bizNo       :</td><td><input type="text" name=bizNo value="${FactoryId.bizNo}"></td></tr>
<tr><td>factorytype :</td><td><input type="text" name=factorytype value="${FactoryId.factorytype}"></td></tr>
<tr><td>fctryNm     :</td><td><input type="text" name=fctryNm value="${FactoryId.fctryNm}"></td></tr>
<tr><td>erpcode     :</td><td><input type="text" name=erpcode value="${FactoryId.erpcode}"></td></tr>
<tr><td>tph1        :</td><td><input type="text" name=tph1 value="${FactoryId.tph1}"></td></tr>
</table>
<input type="submit" value="저장" ><input type="reset" value="취소" ><br>
</form>
<br>
</body>
</html>