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
<c:forEach  var="List" items="${List}">

${List.memberCorpid} : ${List.factoryid}
 <a href="readDataWithKey?memberCorpid=${List.memberCorpid}&factoryid=${List.factoryid}">조회 </a>
<a href="updateDataWithKey?memberCorpid=${List.memberCorpid}&factoryid=${List.factoryid}">수정</a>
<a href="deleteDataWithKey?memberCorpid=${List.memberCorpid}&factoryid=${List.factoryid}">삭제</a>
<br>
</c:forEach>
</body>
</html>