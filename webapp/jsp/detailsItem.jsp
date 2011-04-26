<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="j" uri="/struts-jquery-tags"%>
<j:head jqueryui="true" jquerytheme="humanity" />
<%@ page import="java.util.*" %>

<%
	Date startAuc = (Date) session.getAttribute("startAuc");
	Date finishAuc = (Date) session.getAttribute("finishAuc");
	Date startReg = (Date) session.getAttribute("startReg");
	Date finishReg = (Date) session.getAttribute("finishReg");
	Boolean seller = (Boolean) session.getAttribute ("seller");
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
	Boolean isRegestered = (Boolean) session.getAttribute("isRegistered");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Userlist</title>
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
		<div id="detailsItem" style="margin-left: 250px">
				<s:hidden name="itemId"/>
					<table align="left" cellpadding="5px" class="ui-widget-content userTable">
							<tr>
								<td><img src="${item.photo}" width="200px" height="200px"></td>
								<td>
									<table cellpadding="5px">
									<tr>
										<td>${item.name}</td>
										<td>${item.type}</td>
									</tr>
									<tr>
										<td colspan="2">Описание: ${item.description}</td>
									</tr>
									<tr>
										<td colspan="2">Стартовая цена: ${item.startBid}</td>
									</tr>
									<tr>
										<td colspan="2">Срок регистрации:<br>${item.startReg} - ${item.finishReg}</td>
									</tr>
									<tr>
										<td colspan="2">Срок проведения:<br>${item.startAuc} - ${item.finishAuc}</td>
									</tr>
									</table>
								</td>
							</tr> 
							<%  Calendar cal = Calendar.getInstance();
								if ((seller != null) && (seller)) {	
									if (startAuc.compareTo(cal.getTime()) > 0) { %>
							<tr>
								<td><s:url id="editItemURL" action="editItemInfo">
									<s:param name="itemId" value="%{item.id}"></s:param>
									</s:url> <s:a href="%{editItemURL}">Редактировать</s:a>
								</td>
								<td><s:url id="removeItem" action="removeItem">
									<s:param name="itemId" value="%{item.id}"></s:param>
									</s:url> <s:a href="%{removeItem}">Удалить</s:a>
								</td>
							</tr>
							<%		}
								}
								if (seller == null) {
									if ((startReg.compareTo(cal.getTime()) < 0) && (finishReg.compareTo(cal.getTime()) > 0) && (isRegestered != null) && !isRegestered) { %>
							<tr>
								<td colspan="2" align="center"><s:url id="regURL" action="registerAuc">
									<s:param name="itemId" value="%{item.id}"></s:param>
									</s:url> <s:a href="%{regURL}">Принять участие</s:a>
								</td>
							</tr>	
									<%} if ((startAuc.compareTo(cal.getTime()) < 0) && (finishAuc.compareTo(cal.getTime()) > 0) && (isRegestered != null) && isRegestered) { %>
							<tr>
								<td colspan="2" align="center">
									<table cellpadding="5px">
									<s:form id="addBid" action="newBid">
										<s:hidden name="itemID" value="%{item.id}" />
										Сделать ставку: <s:textfield name="amount" />
										<s:submit value="Поставить"/>
									</s:form>
									</table>
								</td>
							</tr>
							<%		}
								}
							%>
							
					</table>
		</div>
	</div>
</body>
</html>