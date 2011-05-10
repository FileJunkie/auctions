<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/js/functions.jsp" />

<%
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
	Boolean seller = (Boolean) session.getAttribute ("seller");
	String email = (String) session.getAttribute ("email");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item list</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body style="background-image: url(images/bg.jpg)">
	<div align="center"><img alt="Auction" src="images/aucHead.png" align="middle"></div><br/>
	
	<div align="right" style="padding-right: 30px">
		<h3>${email}</h3>
		<%
			if (email != null) {
		%>
		<s:url id="logout" action="logout" escapeAmp="false"></s:url>
		<s:a href="%{logout}"><img src="images/auc_exit.png" border="0"></s:a>
		<%} %>
	</div>
	<div>
		<div style="float: left; width: 250px;">
			<a href="index.jsp"><img src="images/auc_main.png" ALIGN="middle" border="0" style="padding-left: 30px"></a>
			
			<%
			if (email != null) {
			%>
			<s:url id="items" action="itemsList" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
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
		<div id="itemList" style="margin-left: 250px">
			<s:if test="%{itemList.size() > 0}">
				<p><b>Все лоты:</b></p>
				<s:hidden name="itemId"/>
					<table align="left" cellpadding="5px" class="ui-widget-content userTable">
						<s:iterator value="itemList" status="stat">
							<tr>
								<td><img src="<s:property value='photo'/>" width="200px" height="200px"></td>
								<td>
									<table cellpadding="5px">
									<tr>
										<td><s:property value="name" /></td>
										<td><s:property value="type" />
									</tr>
									<tr>
										<td colspan="2">Описание: <s:property value="description" /></td>
									</tr>
									<tr>
										<td colspan="2">Стартовая цена: <s:property value="startBid" /></td>
									</tr>
									<tr>
										<td colspan="2">Срок регистрации:<br><s:property value="startReg"/> - <s:property value="finishReg"/></td>
									</tr>
									<tr>
										<td colspan="2">Срок проведения:<br><s:property value="startAuc"/> - <s:property value="finishAuc"/></td>
									</tr>
									<%
										if (email != null) {
									%>
									<tr>
										<td colspan="2" align="right">
											<s:url id="detail" action="detailItem">
												<s:param name="itemId" value="%{id}"></s:param>
											</s:url> <s:a href="%{detail}">Подробнее...</s:a>
										</td>
									</tr>
									<%} %>
									</table>
								</td>
							</tr>
						</s:iterator> 
					</table>
			</s:if>
			<s:else>
				<p><b>Лоты отсутствуют</b></p>
			</s:else>
		</div>
	</div>
</body>
</html>