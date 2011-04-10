<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
	<s:url id="list" action="listUser" escapeAmp="false"></s:url>
	<s:a href="%{list}">List of users</s:a>
	
	<s:url id="register" action="register" escapeAmp="false"></s:url>
	<s:a href="%{register}">Registration</s:a>
</body>
</html>
