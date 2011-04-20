<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/js/functions.jsp" />

<%
	String email = (String) session.getAttribute("email");
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blacklist Page</title>
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
			
			<s:url id="userRoom" action="userRoom" escapeAmp="false"></s:url>
			<s:a href="%{userRoom}"><img src="images/auc_room.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="items" action="itemsList" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="admin" action="adminConsole" escapeAmp="false"></s:url>
			<s:a href="%{admin}"><img src="images/auc_admin.png" ALIGN="middle" border="0" style="padding-left: 25px"></s:a> 
		</div>
		<div id="newBan" class="register" style="margin-left: 250px">
			<s:form id="ban_form" action="addBan">
				<table cellpadding="3" cellspacing="0">
					<tr>
						<td>Email</td>
						<td><s:textfield id="email" name="email" /></td>
						<td><input type='button' value="Add" onclick="javascript:submit();"></td>
					</tr>
				</table>
			</s:form>
		</div>
		<br>
		<div id="blacklist" style="margin-left: 250px">
			<s:if test="%{result.size() > 0}">
			<div>
				<table cellpadding="5px">
					<tr>
						<s:hidden name="id"/>
						<th>Email</th>
						<th>Remove</th>
					</tr>
					<s:iterator value="result" status="userStatus">
						<tr>
							<td><s:property value="email"/></td>
							<td><s:url id="remove" action="unban">
									<s:param name="id" value="%{id}"></s:param>
								</s:url> <s:a href="%{remove}">Remove</s:a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			</s:if>
		</div>
	</div>
</body>
</html>