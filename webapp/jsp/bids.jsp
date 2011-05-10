<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{bidList.size() > 0}">
	<s:if test="%{aucType == 'English'}">
	<table cellpadding="5px">
		<s:iterator value="bidList" status="stat">
			<tr>
				<td><s:property value="user" /></td>
				<td><s:property value="amount" /></td>
				<td>${time}</td>
			</tr>
		</s:iterator>
	</table>
	</s:if>
	<s:else>
	<table cellpadding="5px">
		<tr>
			<td colspan="2" align="center">${lastBid} <s:submit action="agree" value="Agree" /></td>
		</tr>
		<s:iterator value="bidList" status="stat">
			<tr>
				<td><s:property value="amount" /></td>
				<td>${time}</td>
			</tr>
		</s:iterator>
	</table>
	</s:else>
</s:if>