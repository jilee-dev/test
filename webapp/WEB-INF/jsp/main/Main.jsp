<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form id="targer" action="/short">
		단축할 url : <input type="text" name="url" value="${url}"/>
		<input type="submit" value="Go" />
	</form>
	
	단축된 url : <input type="text" name="shortenUrl" value="${shortenUrl}" readonly="readonly"/>
</body>
</html>