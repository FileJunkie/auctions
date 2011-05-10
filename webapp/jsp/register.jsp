<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="<s:url value="/style.css"/>"/>
<jsp:include page="/js/functions.jsp" />

<%
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
	Boolean seller = (Boolean) session.getAttribute ("seller");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body style="background-image: url(images/bg.jpg)">
	<div align="center"><img alt="Auction" src="images/aucHead.png" align="middle"></div><br/>
	
	<div align="right" style="padding-right: 30px">
		<h3>${email}</h3>
		<s:url id="logout" action="logout" escapeAmp="false"></s:url>
		<s:a href="%{logout}"><img src="images/auc_exit.png" border="0"></s:a>
	</div>
	<div>
		<div style="float: left; width: 250px;">
			<a href="index.jsp"><img src="images/auc_main.png" ALIGN="middle" border="0" style="padding-left: 30px"></a>
			
			<s:url id="items" action="itemsList" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="userRoom" action="userRoom" escapeAmp="false"></s:url>
			<s:a href="%{userRoom}"><img src="images/auc_room.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
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
		<div id="registration" style="margin-left: 250px"
			class="ui-widget ui-widget-content ui-state-active register">
			<s:form id="register_form" action="insertUser">
			<table cellpadding="3" cellspacing="0">
			<tr>
				<td>Email  </td>
				<td><s:textfield id="email" name="email"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Password  </td>
				<td><s:password id="pass" name="password"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Confirm password  </td>
				<td><s:password id="confPass"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Category  </td>
				<td><s:select name="category" list="categoriesLabels"
						headerKey="" headerValue="Select" label="Select a category"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Type  </td>
				<td><s:select name="type" list="typesLabels" headerKey=""
						headerValue="Select" label="Select a type"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			</table>
			<div align="right" style="padding-right: 20px">
				<input type='button' class="ui-state-default ui-corner-all" value="Register" onclick='jscript:beforeSubmit();'>
			</div>
			</s:form>
		</div>
		</div>
	</body>
</html>