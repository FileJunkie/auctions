<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/js/functions.jsp" />

<%
	String email = (String) session.getAttribute ("email");
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
	Boolean seller = (Boolean) session.getAttribute ("seller");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body style="background-image: url(images/bg.jpg)">
	<div align="center"><img alt="Auction" src="images/aucHead.png" align="middle"></div><br/>
	
	<%
		if (email == null) {
	%>
	<div id="login" class="register" align="right" style="padding-right: 30px">
		<s:form id="login_form" action="login">
			<table cellpadding="3" cellspacing="0">
				<tr>
					<td>Логин </td>
					<td colspan="2"><s:textfield id="email" name="email"/></td>
				</tr>
				<tr>
					<td>Пароль </td>
					<td colspan="2"><s:password id="pass" name="password"/></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="image" src="images/auc_enter.png" height="40px" width="60px" border="0" onclick='javascript:submit();'></td>  <!-- тут будут ссылки в виде картинок -->
					<s:url id="register" action="register" escapeAmp="false"></s:url>
					<td><s:a href="%{register}"><img src="images/auc_reg.png" height="40px" width="110px" border="0"></s:a></td>
				</tr>
			</table>
		</s:form>
	</div>
	<%
		} else {
	%>
	<div align="right" style="padding-right: 30px">
		<h3>${email}</h3>
		<s:url id="logout" action="logout" escapeAmp="false"></s:url>
		<s:a href="%{logout}"><img src="images/auc_exit.png" border="0"></s:a>
	</div>
	<%
		}
	%>
	<div>
		<div style="float: left; width: 250px;">
			<a href="index.jsp"><img src="images/auc_main.png" ALIGN="middle" border="0" style="padding-left: 30px"></a>
			<s:url id="items" action="itemsListNotReg" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<%
				if (email != null) {
			%>
			<s:url id="userRoom" action="userRoom" escapeAmp="false"></s:url>
			<s:a href="%{userRoom}"><img src="images/auc_room.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<%} %>
			<%
				if ((seller != null) && (seller)) {
			%>
			<s:url id="addItem" action="addItem" escapeAmp="false"></s:url>
			<s:a href="%{addItem}"><img src="images/auc_put_lot.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<%} %>
			<% if ((isAdmin != null) && (isAdmin)) { %>
			<s:url id="admin" action="adminConsole" escapeAmp="false"></s:url>
			<s:a href="%{admin}"><img src="images/auc_admin.png" ALIGN="middle" border="0" style="padding-left: 25px"></s:a> <!-- тут тоже ссылка в виде картинок -->
			<%}%>
			
		</div>
		<div style="float: right; margin-left: 200px">
			<!-- Текст приветствия -->
		</div>
 	</div>
</body>
</html>