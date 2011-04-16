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
	<s:url id="list" action="listUser" escapeAmp="false"></s:url>
	<s:a href="%{list}">List of users</s:a>
	<s:url id="register" action="register" escapeAmp="false"></s:url>
	<s:a href="%{register}">Registration</s:a>
	<s:url id="logout" action="logout" escapeAmp="false"></s:url>
	<s:a href="%{logout}">Log out</s:a>
	<br />
	<div id="login" class="register">
		<s:form id="login_form" action="login">
			<table cellpadding="3" cellspacing="0">
				<tr>
					<td>Email</td>
					<td><s:textfield id="email" name="email" />
					</td>
				</tr>
				<tr>
					<td>Password</td>
					<td><s:password id="pass" name="password" />
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	<div>
		<input type='button' value="Login" onclick='jscript:login();'>
	</div>
</body>
</html>