<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/js/functions.jsp" />

<%
	String email = (String) session.getAttribute ("email");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
	${email}
	<br />
	<s:url id="list" action="adminUserList" escapeAmp="false"></s:url>
	<s:a href="%{list}">User list</s:a>
	<s:url id="blacklist" action="blacklist" escapeAmp="false"></s:url>
	<s:a href="%{blacklist}">Blacklist</s:a>
	<s:url id="itemCategories" action="itemCategoriesList" escapeAmp="false"></s:url>
	<s:a href="%{itemCategories}">Item categories list</s:a>
	<br />
</body>
</html>