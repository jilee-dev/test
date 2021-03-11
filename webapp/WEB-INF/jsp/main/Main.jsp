<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>URL Shortener</title>

</head>
<body>
	<form id="target" action="${pageContext.request.contextPath}/short">
		단축할 url : <input type="text" name="url" value="${url}" size="100px"/>
		<input type="submit" value="Go" />
	</form>
	
	단축된 url : <input type="text" name="shortenUrl" value="${shortenUrl}" size="100px" readonly="readonly"/>
</body>
</html>