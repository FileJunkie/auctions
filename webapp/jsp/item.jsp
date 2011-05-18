<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*" %>
<%
	Date startAuc = (Date) session.getAttribute("startAuc");
	Date finishAuc = (Date) session.getAttribute("finishAuc");
	Date startReg = (Date) session.getAttribute("startReg");
	Date finishReg = (Date) session.getAttribute("finishReg");
	Integer aucState = (Integer) session.getAttribute("aucState");
	Boolean seller = (Boolean) session.getAttribute ("seller");
	Boolean isRegestered = (Boolean) session.getAttribute("isRegistered");
%>

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
				<td colspan="2">Минимальная цена: ${item.min}</td>
			</tr>
			<tr>
				<td colspan="2">Срок регистрации:<br>${item.startReg} - ${item.finishReg}</td>
			</tr>
			<tr>
				<td colspan="2">Срок проведения:<br>${item.startAuc} - ${item.finishAuc}</td>
			</tr>
			<tr>
				<td colspan="2">Поставка:<br>${item.delivery}</td>
			</tr>
			</table>
		</td>
	</tr> 
	<% if (aucState == 1) { 
		Calendar cal = Calendar.getInstance();
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
		<% }
		}
		if (seller == null) {
			if ((startReg.compareTo(cal.getTime()) < 0) && (finishReg.compareTo(cal.getTime()) > 0) && (isRegestered != null) && !isRegestered) { %>
			<tr>
				<td colspan="2" align="center"><s:url id="regURL" action="registerAuc">
					<s:param name="itemId" value="%{item.id}"></s:param>
					</s:url> <s:a href="%{regURL}">Принять участие</s:a>
				</td>
			</tr>	
		<%}
		}%>
							
		<s:if test="%{item.type == 'English'}">
			<% if (seller == null) {
				if ((startAuc.compareTo(cal.getTime()) < 0) && (finishAuc.compareTo(cal.getTime()) > 0) && (isRegestered != null) && isRegestered) { %>
				<tr>
					<td colspan="2" align="center">
						<s:form id="addBid" action="newBid">
							<s:hidden name="itemID" value="%{item.id}" />
								Сделать ставку: <s:textfield name="amount" />
								<s:submit value="Поставить"/>
						</s:form>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
							<s:form id="addAutobid" action="newAutobid">
								<s:hidden name="item" value="%{item.id}" />
								Максимальное значение для автоставки: <s:textfield name="max" />
								<s:submit value="Сделать автоставку"/>
							</s:form>
					</td>
				</tr>
				<%}
				} %>
							
		</s:if>	
							
		<s:else>
		<% if ((seller != null) && (seller)) {
			if ((startAuc.compareTo(cal.getTime()) < 0) && (finishAuc.compareTo(cal.getTime()) > 0)) { %>
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
		<%	}
		  }	%>
		</s:else>
							
		<tr><td colspan="2">Ставки:<td></tr>
		<tr>
			<td colspan="2" align="center">
				<div id="bids"></div>
			</td>
		</tr>
		<%} else if (aucState == 2) { %>
			<tr>
				<td colspan="2" align="center">
					<s:if test="%{winner != null}">
					Аукцион выиграл ${winner}
					</s:if>
					<s:else>
					Аукцион окончен. Победителей нет.
					</s:else>
				</td>
			</tr>
		<%} %>
		</table>